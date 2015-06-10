package salarycalculation.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import salarycalculation.entity.Employee;

/**
 * {@link EmployeeDomain}に対するテストクラス。
 *
 * @author naotake
 */
public class EmployeeDomainTest {

    private EmployeeDomain testee;

    private BusinessDateDomain businessDate;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        Employee entity = new Employee();

        // 入社日
        Calendar joinDate = Calendar.getInstance();
        joinDate.set(2014, (4 - 1), 1);
        entity.setJoinDate(new Date(joinDate.getTime().getTime()));

        testee = new EmployeeDomain(entity);
    }

    @Test
    public void 入社丸3年目の場合_諸手当を_3000_取得できること() {
        setUpNowCalendar(2017, 3, 31);

        // 諸手当
        assertThat(testee.getAllowance(), is(3000));
    }

    @Test
    public void 入社丸3年目の_PL_の場合_諸手当を_13000_取得できること() {
        setUpNowCalendar(2017, 3, 31);
        testee.getEntity().setCapabilityRank("PL");

        // 諸手当
        assertThat(testee.getAllowance(), is(10000 + 3000));
    }

    @Test
    public void 入社丸3年目の_PM_の場合_諸手当を_33000_取得できること() {
        setUpNowCalendar(2017, 3, 31);
        testee.getEntity().setCapabilityRank("PM");

        // 諸手当
        assertThat(testee.getAllowance(), is(30000 + 3000));
    }

    @Test
    public void 入社丸5年目の場合_諸手当を_5000_取得できること2() {
        setUpNowCalendar(2019, 3, 31);

        // 諸手当
        assertThat(testee.getAllowance(), is(5000));
    }

    @Test
    public void 入社丸5年目の_PL_の場合_諸手当を_15000_取得できること() {
        setUpNowCalendar(2019, 3, 31);
        testee.getEntity().setCapabilityRank("PL");

        // 諸手当
        assertThat(testee.getAllowance(), is(10000 + 5000));
    }

    @Test
    public void 入社丸5年目の_PM_の場合_諸手当を_35000_取得できること() {
        setUpNowCalendar(2019, 3, 31);
        testee.getEntity().setCapabilityRank("PM");

        // 諸手当
        assertThat(testee.getAllowance(), is(30000 + 5000));
    }

    @Test
    public void 入社丸10年目の場合_諸手当を_10000_取得できること2() {
        setUpNowCalendar(2024, 3, 31);

        // 諸手当
        assertThat(testee.getAllowance(), is(10000));
    }

    @Test
    public void 入社丸10年目の_PL_の場合_諸手当を_20000_取得できること() {
        setUpNowCalendar(2024, 3, 31);
        testee.getEntity().setCapabilityRank("PL");

        // 諸手当
        assertThat(testee.getAllowance(), is(10000 + 10000));
    }

    @Test
    public void 入社丸10年目の_PM_の場合_諸手当を_40000_取得できること() {
        setUpNowCalendar(2024, 3, 31);
        testee.getEntity().setCapabilityRank("PM");

        // 諸手当
        assertThat(testee.getAllowance(), is(30000 + 10000));
    }

    @Test
    public void 入社丸20年目の場合_諸手当を_20000_取得できること2() {
        setUpNowCalendar(2034, 3, 31);

        // 諸手当
        assertThat(testee.getAllowance(), is(20000));
    }

    @Test
    public void 入社丸20年目の_PL_の場合_諸手当を_30000_取得できること() {
        setUpNowCalendar(2034, 3, 31);
        testee.getEntity().setCapabilityRank("PL");

        // 諸手当
        assertThat(testee.getAllowance(), is(10000 + 20000));
    }

    @Test
    public void 入社丸20年目の_PM_の場合_諸手当を_50000_取得できること() {
        setUpNowCalendar(2034, 3, 31);
        testee.getEntity().setCapabilityRank("PM");

        // 諸手当
        assertThat(testee.getAllowance(), is(30000 + 20000));
    }

    private void setUpNowCalendar(int year, int month, int day) {
        // 現在日
        businessDate = new BusinessDateDomain();
        Calendar now = Calendar.getInstance();
        now.set(year, (month - 1), day);
        businessDate.setCalendar(now);

        testee.setBusinessDateDomain(businessDate);
    }
}
