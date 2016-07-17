package salarycalculation.database;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.destination.DriverManagerDestination.with;
import static org.assertj.core.api.Assertions.assertThat;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKeys;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.util.reflection.Whitebox;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.operation.Operation;

import salarycalculation.database.model.WorkRecord;
import salarycalculation.dbsetup.CapabilitySetupSupport;
import salarycalculation.dbsetup.OrganizationSetupSupport;
import salarycalculation.dbsetup.RoleSetupSupport;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link WorkDao}に対するテストクラス。
 *
 * @author naotake
 */
public class WorkDaoTest implements RoleSetupSupport, CapabilitySetupSupport, OrganizationSetupSupport {

    private WorkDao testee;
    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Rule
    public ExpectedException expect = ExpectedException.none();

    /**
     * 事前処理。
     */
    @Before
    public void setUp() throws Exception {
        testee = new WorkDao();

        String url = "jdbc:h2:./data/salary_calculation_test";
        Connection connection = DriverManager.getConnection(url, "sa", "");
        Whitebox.setInternalState(testee, "connection", connection);

        // 事前データの準備
        Operation truncate = deleteAllFrom("work", "employee", "role", "capability", "organization");
        Operation role = roleInsert();
        Operation capability = capabilityInsert();
        Operation organization = organizationInsert();
        Operation employee = insertInto("employee").columns("no", "organization", "name", "roleRank",
                                                            "capabilityRank")
                                                   .values(1, "DEV2", "愛媛 蜜柑", "A3", "SE").build();
        Operation work = insertInto("work").columns("employeeNo", "workYearMonth")
                                           .values(1, 201504).build();

        // 事前データ投入
        dbSetupTracker.launchIfNecessary(new DbSetup(with("jdbc:h2:./data/salary_calculation_test", "sa", ""),
                                                     sequenceOf(truncate, role, capability, organization, employee,
                                                                work)));
    }

    @Test
    public void 該当社員の稼動情報を取得できること() {
        dbSetupTracker.skipNextLaunch();

        WorkRecord actual = testee.getByYearMonth(1, 201504);

        assertThat(actual.getEmployeeNo()).isEqualTo(1);
        assertThat(actual.getWorkYearMonth()).isEqualTo(201504);
    }

    @Test
    public void 存在しない稼動情報を指定した場合に例外が送出されること() {
        dbSetupTracker.skipNextLaunch();

        expect.expect(RecordNotFoundException.class);
        expect.expect(isClass(WorkRecord.class));
        expect.expect(isKeys(99, 209912));

        testee.getByYearMonth(99, 209912);
    }
}
