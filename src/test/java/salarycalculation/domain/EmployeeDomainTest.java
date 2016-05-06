package salarycalculation.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import salarycalculation.entity.Capability;
import salarycalculation.entity.Employee;
import salarycalculation.entity.Role;

/**
 * {@link EmployeeDomain}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Enclosed.class)
public class EmployeeDomainTest {

    public static class 入社3年未満の場合 extends EmployeeDomainTestBase {

        @Test
        public void 通常の手当を取得できること() {
            setUpNowCalendar(2017, 2, 28);
            testee.getEntity().setCommuteAmount(2500);
            testee.getEntity().setRentAmount(20000);

            // 通勤手当と住宅手当の合計だけ
            assertThat(testee.getAllowance(), is((2500 + 20000)));
        }
    }

    public static class 入社丸3年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_3000_取得できること() {
            setUpNowCalendar(2017, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(3000));
        }

        @Test
        public void PL_の場合_諸手当を_13000_取得できること() {
            setUpNowCalendar(2017, 3, 31);
            testee.getEntity().setCapabilityRank("PL");

            // 諸手当
            assertThat(testee.getAllowance(), is(10000 + 3000));
        }

        @Test
        public void PM_の場合_諸手当を_33000_取得できること() {
            setUpNowCalendar(2017, 3, 31);
            testee.getEntity().setCapabilityRank("PM");

            // 諸手当
            assertThat(testee.getAllowance(), is(30000 + 3000));
        }
    }

    public static class 入社丸5年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_5000_取得できること2() {
            setUpNowCalendar(2019, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(5000));
        }

        @Test
        public void PL_の場合_諸手当を_15000_取得できること() {
            setUpNowCalendar(2019, 3, 31);
            testee.getEntity().setCapabilityRank("PL");

            // 諸手当
            assertThat(testee.getAllowance(), is(10000 + 5000));
        }

        @Test
        public void PM_の場合_諸手当を_35000_取得できること() {
            setUpNowCalendar(2019, 3, 31);
            testee.getEntity().setCapabilityRank("PM");

            // 諸手当
            assertThat(testee.getAllowance(), is(30000 + 5000));
        }
    }

    public static class 入社丸10年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_10000_取得できること2() {
            setUpNowCalendar(2024, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(10000));
        }

        @Test
        public void PL_の場合_諸手当を_20000_取得できること() {
            setUpNowCalendar(2024, 3, 31);
            testee.getEntity().setCapabilityRank("PL");

            // 諸手当
            assertThat(testee.getAllowance(), is(10000 + 10000));
        }

        @Test
        public void PM_の場合_諸手当を_40000_取得できること() {
            setUpNowCalendar(2024, 3, 31);
            testee.getEntity().setCapabilityRank("PM");

            // 諸手当
            assertThat(testee.getAllowance(), is(30000 + 10000));
        }
    }

    public static class 入社丸20年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_20000_取得できること2() {
            setUpNowCalendar(2034, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(20000));
        }

        @Test
        public void PL_の場合_諸手当を_30000_取得できること() {
            setUpNowCalendar(2034, 3, 31);
            testee.getEntity().setCapabilityRank("PL");

            // 諸手当
            assertThat(testee.getAllowance(), is(10000 + 20000));
        }

        @Test
        public void PM_の場合_諸手当を_50000_取得できること() {
            setUpNowCalendar(2034, 3, 31);
            testee.getEntity().setCapabilityRank("PM");

            // 諸手当
            assertThat(testee.getAllowance(), is(30000 + 20000));
        }
    }

    public static class PLの場合 extends EmployeeDomainTestBase {

        /**
         * 事前処理。
         */
        @Before
        @Override
        public void setUp() {
            super.setUp();
            testee.getEntity().setCapabilityRank("PL");
        }

        @Test
        public void 残業代を0で取得できること() {
            assertThat(testee.getOvertimeAmount(201504), is(0));
        }

        @Test
        public void 想定年収を取得できること() {
            assertThat(testee.getAnnualTotalSalaryPlan(), is(10000 * 12));
        }
    }

    public static class PMの場合 extends EmployeeDomainTestBase {

        /**
         * 事前処理。
         */
        @Before
        @Override
        public void setUp() {
            super.setUp();
            testee.getEntity().setCapabilityRank("PM");
        }

        @Test
        public void 残業代を0で取得できること() {
            assertThat(testee.getOvertimeAmount(201504), is(0));
        }

        @Test
        public void 想定年収を取得できること() {
            assertThat(testee.getAnnualTotalSalaryPlan(), is(30000 * 12));
        }
    }

    private static class EmployeeDomainTestBase {

        protected EmployeeDomain testee;

        /**
         * 事前処理。
         */
        @Before
        public void setUp() {
            Employee entity = new Employee();

            // 社員番号
            entity.setNo(100);

            // 入社日
            Calendar joinDate = Calendar.getInstance();
            joinDate.set(2014, (4 - 1), 1);
            entity.setJoinDate(new Date(joinDate.getTime().getTime()));

            // 役割等級
            entity.setRoleRank("A1");

            // 能力等級
            entity.setCapabilityRank("AS");

            // 通勤手当
            entity.setCommuteAmount(0);

            // 住宅手当
            entity.setRentAmount(0);

            testee = new EmployeeDomain(entity);
            testee.setRole(new Role());
            testee.setCapability(new Capability());
        }

        /**
         * {@link BusinessDateDomain}経由で現在日時を特定日に設定する。
         *
         * @param year 年
         * @param month 月
         * @param day 日
         */
        public void setUpNowCalendar(int year, int month, int day) {
            // 現在日
            BusinessDateDomain businessDate = new BusinessDateDomain();
            Calendar now = Calendar.getInstance();
            now.set(year, (month - 1), day);
            businessDate.setCalendar(now);

            testee.setBusinessDateDomain(businessDate);
        }
    }

}
