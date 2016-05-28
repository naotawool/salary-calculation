package salarycalculation.domain;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Employee}に対する EasyMock を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeDomainTest_EasyMock {

    private Employee testee;

    @Test
    public void 入社年数を取得できること() {
        // パーシャルモックを用意
        testee = createMockBuilder(Employee.class).addMockedMethod("calculateAttendanceMonth").createMock();

        // 振る舞いを定義
        expect(testee.calculateAttendanceMonth()).andReturn(12).andReturn(24).andReturn(36);

        // 再生
        replay(testee);

        // 実行
        assertThat(testee.getDurationYear(), is(1)); // Month: 12
        assertThat(testee.getDurationYear(), is(2)); // Month: 24
        assertThat(testee.getDurationYear(), is(3)); // Month: 36

        // 検証
        verify(testee);
    }
}
