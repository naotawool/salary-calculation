package salarycalculation.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import salarycalculation.entity.Capability;
import salarycalculation.entity.Employee;
import salarycalculation.entity.Role;

/**
 * {@link EmployeeDomain}に対する Mockito を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeDomainTest_Mockito {

    private EmployeeDomain testee;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = mock(EmployeeDomain.class);
    }

    @Test
    public void 入社年数を取得できること() {
        // 振る舞いを定義
        when(testee.getDurationMonth()).thenReturn(12, 24, 36);
        when(testee.getDurationYear()).thenCallRealMethod();

        // 実行
        assertThat(testee.getDurationYear(), is(1)); // Month: 12
        assertThat(testee.getDurationYear(), is(2)); // Month: 24
        assertThat(testee.getDurationYear(), is(3)); // Month: 36

        // 検証
        verify(testee, times(3)).getDurationMonth();
    }

    @Test
    public void spyを使用して入社年数を取得できること() {
        setUpSpy();

        // 振る舞いを定義
        doReturn(12).doReturn(24).doReturn(36).when(testee).getDurationMonth();

        // 実行
        assertThat(testee.getDurationYear(), is(1)); // Month: 12
        assertThat(testee.getDurationYear(), is(2)); // Month: 24
        assertThat(testee.getDurationYear(), is(3)); // Month: 36

        // 検証
        verify(testee, times(3)).getDurationMonth();
    }

    @Test
    public void 総支給額と控除額を使って手取り額を取得できること() {
        // 控除額を設定
        Employee entity = new Employee();
        entity.setHealthInsuranceAmount(1);
        entity.setEmployeePensionAmount(2);
        entity.setIncomeTaxAmount(3);
        entity.setInhabitantTaxAmount(4);

        setUpSpy(entity);

        // 総支給額取得の振る舞いを定義
        doReturn(100).when(testee).getTotalSalary(201504);

        // 実行
        assertThat(testee.getTakeHomeAmount(201504), is((100 - 10)));

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

    private void setUpSpy() {
        setUpSpy(new Employee());
    }

    private void setUpSpy(Employee entity) {
        testee = spy(new EmployeeDomain(entity));
    }

    private Role createRole(int amount) {
        Role role = new Role();
        role.setAmount(amount);
        return role;
    }

    private Capability createCapability(int amount) {
        Capability capability = new Capability();
        capability.setAmount(amount);
        return capability;
    }
}
