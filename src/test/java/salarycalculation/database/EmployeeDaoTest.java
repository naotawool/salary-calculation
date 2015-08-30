package salarycalculation.database;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static salarycalculation.matchers.EmployeeAssertion.isEqualToName;
import static salarycalculation.matchers.EmployeeAssertion.isEqualToNo;
import static salarycalculation.matchers.OrderEmployee.orderNos;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import salarycalculation.entity.Employee;

/**
 * {@link EmployeeDao}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Enclosed.class)
public class EmployeeDaoTest {

    public static class 社員一覧を取得する場合 extends EmployeeDaoTestBase {

        @Test
        public void 社員番号の昇順で取得できること() {
            List<Employee> actuals = testee.findAll(true);

            assertThat(actuals, orderNos(1, 2, 3, 4));
        }

        @Test
        public void 社員番号の降順で取得できること() {
            List<Employee> actuals = testee.findAll(false);

            assertThat(actuals, orderNos(4, 3, 2, 1));
        }

        @Ignore
        @Test
        public void OrderEmployee_orderNosが失敗するテスト() {
            List<Employee> actuals = testee.findAll(false);

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
            List<Employee> actuals = testee.findByRole("A3");

            assertThat(actuals, hasSize(1));
            assertThat(actuals.get(0), isEqualToNo(1));
            assertThat(actuals.get(0), isEqualToName("愛媛 蜜柑"));
        }

        @Test
        public void M3ランクの社員一覧を取得できること() {
            List<Employee> actuals = testee.findByRole("M3");

            assertThat(actuals, empty());
        }
    }

    public static class 能力等級を条件にした場合 extends EmployeeDaoTestBase {

        @Test
        public void SEランクの社員一覧を取得できること() {
            List<Employee> actuals = testee.findByCapability("SE");

            assertThat(actuals, hasSize(1));
            assertThat(actuals.get(0), isEqualToNo(1));
            assertThat(actuals.get(0), isEqualToName("愛媛 蜜柑"));
        }

        @Test
        public void PGランクの社員一覧を取得できること() {
            List<Employee> actuals = testee.findByCapability("PG");

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
