package salarycalculation.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import salarycalculation.entity.EmployeeRecord;
import salarycalculation.utils.Money;

/**
 * {@link EmployeeRecord}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Enclosed.class)
public class EmployeeDomainTest {

    public static class 入社3年未満の場合 extends EmployeeDomainTestBase {

        @Test
        public void 通常の手当を取得できること() {
            setUpNowCalendar(2017, 2, 28);
            testee.setCommuteAmount(Money.from(2500));
            testee.setRentAmount(Money.from(20000));

            // 通勤手当と住宅手当の合計だけ
            assertThat(testee.getAllowance(), is(equalTo(Money.from(2500).add(Money.from(20000)))));
        }
    }

    public static class 入社丸3年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_3000_取得できること() {
            setUpNowCalendar(2017, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(3000))));
        }

        @Test
        public void PL_の場合_諸手当を_13000_取得できること() {
            setUpNowCalendar(2017, 3, 31);
            testee.setCapability(Capability.pl(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(3000).add(Money.from(10000)))));
        }

        @Test
        public void PM_の場合_諸手当を_33000_取得できること() {
            setUpNowCalendar(2017, 3, 31);
            testee.setCapability(Capability.pm(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(3000).add(Money.from(30000)))));
        }
    }

    public static class 入社丸5年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_5000_取得できること2() {
            setUpNowCalendar(2019, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(5000))));
        }

        @Test
        public void PL_の場合_諸手当を_15000_取得できること() {
            setUpNowCalendar(2019, 3, 31);
            testee.setCapability(Capability.pl(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(5000).add(Money.from(10000)))));
        }

        @Test
        public void PM_の場合_諸手当を_35000_取得できること() {
            setUpNowCalendar(2019, 3, 31);
            testee.setCapability(Capability.pm(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(5000).add(Money.from(30000)))));
        }
    }

    public static class 入社丸10年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_10000_取得できること2() {
            setUpNowCalendar(2024, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(10000))));
        }

        @Test
        public void PL_の場合_諸手当を_20000_取得できること() {
            setUpNowCalendar(2024, 3, 31);
            testee.setCapability(Capability.pl(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(10000 + 10000))));
        }

        @Test
        public void PM_の場合_諸手当を_40000_取得できること() {
            setUpNowCalendar(2024, 3, 31);
            testee.setCapability(Capability.pm(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(30000 + 10000))));
        }
    }

    public static class 入社丸20年目の場合 extends EmployeeDomainTestBase {

        @Test
        public void 諸手当を_20000_取得できること2() {
            setUpNowCalendar(2034, 3, 31);

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(20000))));
        }

        @Test
        public void PL_の場合_諸手当を_30000_取得できること() {
            setUpNowCalendar(2034, 3, 31);
            testee.setCapability(Capability.pl(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(10000 + 20000))));
        }

        @Test
        public void PM_の場合_諸手当を_50000_取得できること() {
            setUpNowCalendar(2034, 3, 31);
            testee.setCapability(Capability.pm(Money.ZERO));

            // 諸手当
            assertThat(testee.getAllowance(), is(equalTo(Money.from(30000 + 20000))));
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
            testee.setCapability(Capability.pl(Money.ZERO));
        }

        @Test
        public void 残業代を0で取得できること() {
            assertThat(testee.getOvertimeAmount(201504), is(Money.ZERO));
        }

        @Test
        public void 想定年収を取得できること() {
            assertThat(testee.getAnnualTotalSalaryPlan(), is(equalTo(Money.from(10000).multiply(12))));
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
            testee.setCapability(Capability.pm(Money.ZERO));
        }

        @Test
        public void 残業代を0で取得できること() {
            assertThat(testee.getOvertimeAmount(201504), is(Money.ZERO));
        }

        @Test
        public void 想定年収を取得できること() {
            assertThat(testee.getAnnualTotalSalaryPlan(), is(equalTo(Money.from(30000).multiply(12))));
        }
    }

    private static class EmployeeDomainTestBase {

        protected Employee testee;

        /**
         * 事前処理。
         */
        @Before
        public void setUp() {
            // 社員番号
            testee = new Employee(1);

            // 入社日
            testee.setJoinDate(BusinessDate.of(2014, 4, 1));

            // 役割等級
            testee.setRole(new Role("A1", Money.ZERO));

            // 能力等級
            testee.setCapability(Capability.normal(CapabilityRank.AS, Money.ZERO));

            // 通勤手当
            testee.setCommuteAmount(Money.ZERO);

            // 住宅手当
            testee.setRentAmount(Money.ZERO);

        }

        /**
         * {@link BusinessDate}経由で現在日時を特定日に設定する。
         *
         * @param year 年
         * @param month 月
         * @param day 日
         */
        public void setUpNowCalendar(int year, int month, int day) {
            // 現在日
            Calendar now = Calendar.getInstance();
            now.set(year, (month - 1), day);
            BusinessDate businessDate = BusinessDate.of(now);

            testee.setNow(businessDate);
        }
    }

}
