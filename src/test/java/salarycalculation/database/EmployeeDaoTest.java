package salarycalculation.database;

import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.collection.IsEmptyCollection.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static salarycalculation.matchers.EmployeeAssertion.*;
import static salarycalculation.matchers.OrderEmployee.*;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import salarycalculation.entity.EmployeeRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link EmployeeDao}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Enclosed.class)
public class EmployeeDaoTest {

    public static class 社員情報を1件取得する場合 extends EmployeeDaoTestBase {

        @Rule
        public ExpectedException expect = ExpectedException.none();

        @Test
        public void 社員情報を取得できること() {
            EmployeeRecord actual = testee.get("1");
            assertThat(actual, isEqualToNo(1));
            assertThat(actual, isEqualToName("愛媛 蜜柑"));
        }

        @Test
        public void testtestName() {
            expect.expect(RecordNotFoundException.class);
            expect.expect(isClass(EmployeeRecord.class));
            expect.expect(isKey("9999"));

            testee.get("9999");
        }
    }

    public static class 社員一覧を取得する場合 extends EmployeeDaoTestBase {

        @Test
        public void 社員番号の昇順で取得できること() {
            List<EmployeeRecord> actuals = testee.findAll(true);

            assertThat(actuals, orderNos(1, 2, 3, 4));
        }

        @Test
        public void 社員番号の降順で取得できること() {
            List<EmployeeRecord> actuals = testee.findAll(false);

            assertThat(actuals, orderNos(4, 3, 2, 1));
        }

        @Ignore
        @Test
        public void OrderEmployee_orderNosが失敗するテスト() {
            List<EmployeeRecord> actuals = testee.findAll(false);

            assertThat(actuals, orderNos(4, 3, 2));
        }

        @Test
        public void 指定した組織に該当する社員数を取得できること() {
            assertThat(testee.countByOrganization("ODG1"), is(2L));
            assertThat(testee.countByOrganization("ODG2"), is(1L));
            assertThat(testee.countByOrganization("ODG3"), is(1L));
            assertThat(testee.countByOrganization("OD00"), is(0L));
        }
    }

    public static class 役割等級を条件にした場合 extends EmployeeDaoTestBase {

        @Test
        public void A3ランクの社員一覧を取得できること() {
            List<EmployeeRecord> actuals = testee.findByRole("A3");

            assertThat(actuals, hasSize(1));
            assertThat(actuals.get(0), isEqualToNo(1));
            assertThat(actuals.get(0), isEqualToName("愛媛 蜜柑"));
        }

        @Test
        public void M3ランクの社員一覧を取得できること() {
            List<EmployeeRecord> actuals = testee.findByRole("M3");

            assertThat(actuals, empty());
        }
    }

    public static class 能力等級を条件にした場合 extends EmployeeDaoTestBase {

        @Test
        public void SEランクの社員一覧を取得できること() {
            List<EmployeeRecord> actuals = testee.findByCapability("SE");

            assertThat(actuals, hasSize(1));
            assertThat(actuals.get(0), isEqualToNo(1));
            assertThat(actuals.get(0), isEqualToName("愛媛 蜜柑"));
        }

        @Test
        public void PGランクの社員一覧を取得できること() {
            List<EmployeeRecord> actuals = testee.findByCapability("PG");

            assertThat(actuals, empty());
        }
    }

    private static class EmployeeDaoTestBase {

        protected EmployeeDao testee;

        /**
         * 事前処理。
         */
        @Before
        public void setUp() {
            testee = new EmployeeDao();
        }
    }
}
