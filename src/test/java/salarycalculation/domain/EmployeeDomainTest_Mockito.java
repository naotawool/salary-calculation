package salarycalculation.domain;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import salarycalculation.database.WorkDao;
import salarycalculation.entity.EmployeeRecord;
import salarycalculation.entity.RoleRecord;
import salarycalculation.entity.WorkRecord;
import salarycalculation.utils.Money;

/**
 * {@link EmployeeRecord}に対する Mockito を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeDomainTest_Mockito {

    private Employee testee;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = mock(Employee.class);
    }

    @Test
    public void 入社年数を取得できること() {
        // 振る舞いを定義
        when(testee.calculateAttendanceMonth()).thenReturn(12, 24, 36);
        when(testee.getDurationYear()).thenCallRealMethod();

        // 実行
        assertThat(testee.getDurationYear(), is(1)); // Month: 12
        assertThat(testee.getDurationYear(), is(2)); // Month: 24
        assertThat(testee.getDurationYear(), is(3)); // Month: 36

        // 検証
        verify(testee, times(3)).calculateAttendanceMonth();
    }

    @Test
    public void spyを使用して入社年数を取得できること() {
        setUpSpy();

        // 振る舞いを定義
        doReturn(12).doReturn(24).doReturn(36).when(testee).calculateAttendanceMonth();

        // 実行
        assertThat(testee.getDurationYear(), is(1)); // Month: 12
        assertThat(testee.getDurationYear(), is(2)); // Month: 24
        assertThat(testee.getDurationYear(), is(3)); // Month: 36

        // 検証
        verify(testee, times(3)).calculateAttendanceMonth();
    }

    @Test
    public void 総支給額と控除額を使って手取り額を取得できること() {
        // 控除額を設定
        Employee entity = new Employee(1);
        entity.setHealthInsuranceAmount(Money.from(1));
        entity.setEmployeePensionAmount(Money.from(2));
        entity.setIncomeTaxAmount(Money.from(3));
        entity.setInhabitantTaxAmount(Money.from(4));

        setUpSpy(entity);

        // 総支給額取得の振る舞いを定義
        doReturn(Money.from(100)).when(testee).getTotalSalary(201504);

        // 実行
        assertThat(testee.getTakeHomeAmount(201504), is((Money.from(100 - 10))));

        // 検証
        verify(testee).getTotalSalary(201504);
    }

    @Test
    public void 総支給額を取得できること() {
        // 役割等級額を設定
        Role role = createRole(60);
        // 能力等級額を設定
        Capability capability = createCapability(40);

        setUpSpy();
        testee.setRole(role);
        testee.setCapability(capability);

        // 諸手当取得の振る舞いを定義
        doReturn(30).when(testee).getAllowance();
        // 基準外給与取得の振る舞いを定義
        doReturn(20).when(testee).getOvertimeAmount(201504);

        // 実行
        assertThat(testee.getTotalSalary(201504), is((60 + 40 + 30 + 20)));

        // 検証
        verify(testee).getAllowance();
        verify(testee).getOvertimeAmount(201504);
    }

    @Test
    public void 社員の残業代を取得できること() {
        // 時間外手当を設定
        Employee entity = new Employee(101);
        entity.setWorkOverTime1hAmount(Money.from(10));
        entity.setCapability(Capability.normal(CapabilityRank.AS, Money.ZERO));

        setUpSpy(entity);

        // Work 情報を設定
        WorkRecord work = new WorkRecord();
        work.setWorkOverTime(BigDecimal.valueOf(10L));
        work.setLateNightOverTime(BigDecimal.valueOf(20L));
        work.setHolidayWorkTime(BigDecimal.valueOf(30L));
        work.setHolidayLateNightOverTime(BigDecimal.valueOf(40L));

        // 期待値を求める
        int expected = (10) * 10;
        expected += (10 * 1.1) * 20;
        expected += (10 * 1.2) * 30;
        expected += (10 * 1.3) * 40;

        // 実行
        assertThat(testee.getOvertimeAmount(201504), is(Money.from(expected)));

        // 検証
        // TODO
        //verify(mockWorkDao).getByYearMonth(101, 201504);
    }

    private void setUpSpy() {
        setUpSpy(new Employee(1));
    }

    private void setUpSpy(Employee entity) {
        testee = spy(entity);
    }

    private Role createRole(int amount) {
        return new Role("dummy", Money.from(amount));
    }

    private Capability createCapability(int amount) {
        return Capability.normal(CapabilityRank.AS, Money.from(amount));
    }
}
