package salarycalculation.database;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.destination.DriverManagerDestination.with;
import static org.assertj.core.api.Assertions.assertThat;
import static salarycalculation.assertions.EmployeeRecordAssert.assertThat;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.operation.Operation;

import salarycalculation.database.model.EmployeeRecord;
import salarycalculation.dbsetup.CapabilitySetupSupport;
import salarycalculation.dbsetup.OrganizationSetupSupport;
import salarycalculation.dbsetup.RoleSetupSupport;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link EmployeeDao}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Enclosed.class)
public class EmployeeDaoTest {

    public static class 社員情報を1件取得する場合 extends EmployeeDaoTestBase {

        @BeforeClass
        public static void setUpOnce() throws Exception {
            dbSetupTracker = new DbSetupTracker();
        }

        @Rule
        public ExpectedException expect = ExpectedException.none();

        @Test
        public void 社員情報を取得できること() {
            dbSetupTracker.skipNextLaunch();

            EmployeeRecord actual = testee.get("1");
            assertThat(actual).isNotNull().hasNo(1).hasName("愛媛 蜜柑");
        }

        @Test
        public void 存在しない社員番号を指定して例外が発生すること() {
            dbSetupTracker.skipNextLaunch();

            expect.expect(RecordNotFoundException.class);
            expect.expect(isClass(EmployeeRecord.class));
            expect.expect(isKey("9999"));

            testee.get("9999");
        }
    }

    public static class 社員一覧を取得する場合 extends EmployeeDaoTestBase {

        @BeforeClass
        public static void setUpOnce() throws Exception {
            dbSetupTracker = new DbSetupTracker();
        }

        @Test
        public void 社員番号の昇順で取得できること() {
            dbSetupTracker.skipNextLaunch();

            List<EmployeeRecord> actuals = testee.findAll(true);
            assertThat(actuals).extracting(EmployeeRecord::getNo).containsExactly(1, 2, 3, 4);
        }

        @Test
        public void 社員番号の降順で取得できること() {
            dbSetupTracker.skipNextLaunch();

            List<EmployeeRecord> actuals = testee.findAll(false);
            assertThat(actuals).extracting(EmployeeRecord::getNo).containsExactly(4, 3, 2, 1);
        }

        @Ignore
        @Test
        public void OrderEmployee_orderNosが失敗するテスト() {
            dbSetupTracker.skipNextLaunch();

            List<EmployeeRecord> actuals = testee.findAll(false);
            assertThat(actuals).extracting(EmployeeRecord::getNo).containsExactly(4, 3, 2);
        }

        @Test
        public void 指定した組織に該当する社員数を取得できること() {
            dbSetupTracker.skipNextLaunch();

            assertThat(testee.countByOrganization("DEV1")).isEqualTo(2);
            assertThat(testee.countByOrganization("DEV2")).isEqualTo(1);
            assertThat(testee.countByOrganization("DEV3")).isEqualTo(1);
            assertThat(testee.countByOrganization("DEV")).isEqualTo(0);
        }
    }

    public static class 役割等級を条件にした場合 extends EmployeeDaoTestBase {

        @BeforeClass
        public static void setUpOnce() throws Exception {
            dbSetupTracker = new DbSetupTracker();
        }

        @Test
        public void A3ランクの社員一覧を取得できること() {
            dbSetupTracker.skipNextLaunch();

            List<EmployeeRecord> actuals = testee.findByRole("A3");
            assertThat(actuals).hasSize(1);
            assertThat(actuals.get(0)).hasNo(1).hasName("愛媛 蜜柑");
        }

        @Test
        public void M3ランクの社員一覧を取得できること() {
            dbSetupTracker.skipNextLaunch();

            List<EmployeeRecord> actuals = testee.findByRole("M3");
            assertThat(actuals).isEmpty();
        }
    }

    public static class 能力等級を条件にした場合 extends EmployeeDaoTestBase {

        @BeforeClass
        public static void setUpOnce() throws Exception {
            dbSetupTracker = new DbSetupTracker();
        }

        @Test
        public void SEランクの社員一覧を取得できること() {
            dbSetupTracker.skipNextLaunch();

            List<EmployeeRecord> actuals = testee.findByCapability("SE");
            assertThat(actuals).hasSize(1);
            assertThat(actuals.get(0)).hasNo(1).hasName("愛媛 蜜柑");
        }

        @Test
        public void PGランクの社員一覧を取得できること() {
            dbSetupTracker.skipNextLaunch();

            List<EmployeeRecord> actuals = testee.findByCapability("PG");
            assertThat(actuals).isEmpty();
        }
    }

    private static class EmployeeDaoTestBase
            implements RoleSetupSupport, CapabilitySetupSupport, OrganizationSetupSupport {

        protected EmployeeDao testee;

        protected static DbSetupTracker dbSetupTracker;

        /**
         * 事前処理。
         */
        @Before
        public void setUp() throws Exception {
            testee = new EmployeeDao();

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
                                                       .values(1, "DEV2", "愛媛 蜜柑", "A3", "SE")
                                                       .values(2, "DEV1", "大阪 太郎", "C4", "PL")
                                                       .values(3, "DEV1", "埼玉 花子", "M2", "PM")
                                                       .values(4, "DEV3", "東京 次郎", "A1", "AS").build();

            // 事前データ投入
            dbSetupTracker.launchIfNecessary(new DbSetup(with("jdbc:h2:./data/salary_calculation_test", "sa", ""),
                                                         sequenceOf(truncate, role, capability, organization,
                                                                    employee)));
        }
    }
}
