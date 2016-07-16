package salarycalculation.domain.employee;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.destination.DriverManagerDestination.with;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static salarycalculation.matchers.OrderEmployeeDomain.orderNos;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.yaml.snakeyaml.Yaml;

import com.google.common.io.Resources;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.operation.Insert;
import com.ninja_squad.dbsetup.operation.Operation;

import salarycalculation.database.CapabilityDao;
import salarycalculation.database.EmployeeDao;
import salarycalculation.database.OrganizationDao;
import salarycalculation.database.RoleDao;
import salarycalculation.database.WorkDao;
import salarycalculation.database.repository.EmployeeRepositoryDao;
import salarycalculation.database.repository.EmployeeTransformer;
import salarycalculation.database.repository.OrganizationRepositoryDao;
import salarycalculation.database.repository.WorkRepositoryDao;
import salarycalculation.dbsetup.CapabilitySetupSupport;
import salarycalculation.dbsetup.OrganizationSetupSupport;
import salarycalculation.dbsetup.RoleSetupSupport;
import salarycalculation.domain.organization.OrganizationRepository;

/**
 * {@link EmployeeRepository}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Theories.class)
public class EmployeeRepositoryTest
        implements RoleSetupSupport, CapabilitySetupSupport, OrganizationSetupSupport {

    private EmployeeRepositoryDao testee;

    @DataPoint
    public static Fixture fixture1 = new Fixture("1", 1, "愛媛 蜜柑", "A3", "SE", "開発部2");
    @DataPoint
    public static Fixture fixture2 = new Fixture("2", 2, "大阪 太郎", "C4", "PL", "開発部1");
    @DataPoint
    public static Fixture fixture3 = new Fixture("3", 3, "埼玉 花子", "M2", "PM", "開発部1");
    @DataPoint
    public static Fixture fixture4 = new Fixture("4", 4, "東京 次郎", "A1", "AS", "開発部3");

    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        testee = new EmployeeRepositoryDao();

        // テスト用コネクション
        String connectionUrl = "jdbc:h2:./data/salary_calculation_test";
        Connection connection = DriverManager.getConnection(connectionUrl, "sa", "");

        // EmployeeDao 初期化
        initializeEmployeeDao(connection);

        // OrganizationRepository 初期化
        initializeOrganizationRepository(connection);

        // EmployeeTransformer 初期化
        initializeEmployeeTransformer(connection);

        // 事前データの準備
        Operation truncate = deleteAllFrom("work", "employee", "role", "capability", "organization");
        Operation role = roleInsert();
        Operation capability = capabilityInsert();
        Operation organization = organizationInsert();

        String path = getClass().getCanonicalName().replace('.', '/') + ".json";
        // path: salarycalculation/domain/employee/EmployeeRepositoryTest.json

        URL url = Resources.getResource(path);
        Map<String, Object> dataset = (Map<String, Object>) new Yaml().load(url.openStream());

        List<Insert> inserts = new ArrayList<>();
        for (Map.Entry<String, Object> tables : dataset.entrySet()) {
            Insert.Builder target = insertInto(tables.getKey());

            List<Map<String, Object>> rows = (List<Map<String, Object>>) tables.getValue();
            if (rows.isEmpty()) {
                continue;
            }

            for (Map<String, Object> row : rows) {
                target = target.values(row);
            }
            inserts.add(target.build());
        }

        // 事前データ投入
        dbSetupTracker.launchIfNecessary(new DbSetup(with(connectionUrl, "sa", ""),
                                                     sequenceOf(truncate, role, capability, organization,
                                                                sequenceOf(inserts))));
    }

    private void initializeEmployeeDao(Connection connection) {
        EmployeeDao employeeDao = new EmployeeDao();
        Whitebox.setInternalState(employeeDao, "connection", connection);

        testee.setDao(employeeDao);
    }

    private void initializeOrganizationRepository(Connection connection) {
        OrganizationDao organizationDao = new OrganizationDao();
        Whitebox.setInternalState(organizationDao, "connection", connection);

        OrganizationRepository organizationRepositoryDao = new OrganizationRepositoryDao();
        Whitebox.setInternalState(organizationRepositoryDao, "dao", organizationDao);

        testee.setOrganizationDao(organizationRepositoryDao);
    }

    private void initializeEmployeeTransformer(Connection connection) {
        RoleDao roleDao = new RoleDao();
        Whitebox.setInternalState(roleDao, "connection", connection);

        CapabilityDao capabilityDao = new CapabilityDao();
        Whitebox.setInternalState(capabilityDao, "connection", connection);

        WorkDao workDao = new WorkDao();
        Whitebox.setInternalState(workDao, "connection", connection);

        WorkRepositoryDao workRepositoryDao = new WorkRepositoryDao();
        Whitebox.setInternalState(workRepositoryDao, "dao", workDao);

        OrganizationDao organizationDao = new OrganizationDao();
        Whitebox.setInternalState(organizationDao, "connection", connection);

        OrganizationRepository organizationRepositoryDao = new OrganizationRepositoryDao();
        Whitebox.setInternalState(organizationRepositoryDao, "dao", organizationDao);

        EmployeeTransformer transformer = new EmployeeTransformer();
        transformer.setOrganizationRepository(organizationRepositoryDao);
        transformer.setRoleDao(roleDao);
        transformer.setCapabilityDao(capabilityDao);
        transformer.setWorkRepository(workRepositoryDao);

        testee.setTransFormer(transformer);
    }

    @Theory
    public void 社員番号を基に情報を取得できること(Fixture fixture) {
        dbSetupTracker.skipNextLaunch();

        Employee actual = testee.get(fixture.no);
        assertThat(actual.getId(), is(fixture.expectedNo));
        assertThat(actual.getName().getFullName(), is(fixture.expectedName));
        assertThat(actual.getRole().getRank(), is(fixture.expectedRoleRank));
        assertThat(actual.getCapability().getRank().name(), is(fixture.expectedCapabilityRank));
        assertThat(actual.getOrganization().getName(), is(fixture.expectedOrganizationName));
    }

    @Test
    public void 社員一覧を取得できること() {
        dbSetupTracker.skipNextLaunch();

        List<Employee> actuals = testee.findAll().getEmployees();

        assertThat(actuals.size(), is(4));
        assertThat(actuals, orderNos(1, 2, 3, 4));
    }

    @Test
    public void 社員一覧を想定年収の昇順に取得できること() {
        dbSetupTracker.skipNextLaunch();

        List<Employee> actuals = testee.findAllOrderByAnnualSalary(true).getEmployees();

        assertThat(actuals.size(), is(4));
        assertThat(actuals, orderNos(4, 1, 2, 3));
    }

    @Test
    public void 社員一覧を想定年収の降順に取得できること() {
        dbSetupTracker.skipNextLaunch();

        List<Employee> actuals = testee.findAllOrderByAnnualSalary(false).getEmployees();

        assertThat(actuals.size(), is(4));
        assertThat(actuals, orderNos(3, 2, 1, 4));
    }

    @Test
    public void 勤続月数の最大最小の社員情報を取得できること() {
        dbSetupTracker.skipNextLaunch();

        // 最大
        Employee actual = testee.getByDurationMonth(true);
        assertThat(actual.getId(), is(3));

        // 最小
        actual = testee.getByDurationMonth(false);
        assertThat(actual.getId(), is(4));
    }

    @Test
    public void 指定年月の全社員の給与総支給額を取得できること() {
        dbSetupTracker.skipNextLaunch();
        assertThat(testee.findAll().getSumTotalSalary(201504), is(1965412));
    }

    @Test
    public void 指定年月の全社員の手取り額平均を取得できること() {
        dbSetupTracker.skipNextLaunch();
        assertThat(testee.findAll().getAverageTakeHome(201504), is(458387));
    }

    @Test
    public void 指定した額を超える想定年収の社員数を取得できること() {
        dbSetupTracker.skipNextLaunch();
        assertThat(testee.findAll().getCountByOverAnnualSalary(4000000), is(3));
        assertThat(testee.findAll().getCountByOverAnnualSalary(5000000), is(2));
    }

    static class Fixture {

        /** 取得対象の社員番号 */
        String no;
        /** 期待する社員番号 */
        int expectedNo;
        /** 期待する社員名 */
        String expectedName;
        /** 期待する役割等級 */
        String expectedRoleRank;
        /** 期待する能力等級 */
        String expectedCapabilityRank;
        /** 期待する組織名 */
        String expectedOrganizationName;

        public Fixture(String no, int expectedNo, String expectedName, String expectedRoleRank,
                       String expectedCapabilityRank, String expectedOrganizationName) {
            this.no = no;
            this.expectedNo = expectedNo;
            this.expectedName = expectedName;
            this.expectedRoleRank = expectedRoleRank;
            this.expectedCapabilityRank = expectedCapabilityRank;
            this.expectedOrganizationName = expectedOrganizationName;
        }
    }
}
