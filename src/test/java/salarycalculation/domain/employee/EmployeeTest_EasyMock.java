package salarycalculation.domain.employee;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Employee}に対する EasyMock を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeTest_EasyMock {

    private Employee testee;

    @Test
    public void 入社年数を取得できること() {
        // パーシャルモックを用意
        testee = createMockBuilder(Employee.class).addMockedMethod("calculateAttendanceMonth").createMock();

        // 振る舞いを定義
        BusinessDate now = BusinessDate.now();
        expect(testee.calculateAttendanceMonth(now)).andReturn(12).andReturn(24).andReturn(36);

        // 再生
        replay(testee);

        // 実行
        assertThat(testee.getDurationYear(now), is(1)); // Month: 12
        assertThat(testee.getDurationYear(now), is(2)); // Month: 24
        assertThat(testee.getDurationYear(now), is(3)); // Month: 36

        // 検証
        verify(testee);
    }
}
