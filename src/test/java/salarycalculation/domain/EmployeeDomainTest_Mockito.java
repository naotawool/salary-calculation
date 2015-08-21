package salarycalculation.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import salarycalculation.entity.Employee;

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
        testee = spy(new EmployeeDomain(new Employee()));

        // 振る舞いを定義
        doReturn(12).doReturn(24).doReturn(36).when(testee).getDurationMonth();

        // 実行
        assertThat(testee.getDurationYear(), is(1)); // Month: 12
        assertThat(testee.getDurationYear(), is(2)); // Month: 24
        assertThat(testee.getDurationYear(), is(3)); // Month: 36

        // 検証
        verify(testee, times(3)).getDurationMonth();
    }
}
