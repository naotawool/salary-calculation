package unittest;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link Sample}に対する JUnit4 を使ったテストクラス。
 *
 * @author naotake
 */
public class SampleJUnit4Test {

    private Sample testee;

    @Before
    public void setUp() throws Exception {
        testee = new Sample();
    }

    @Test
    public void 足し算が正しく行われること() throws Exception {
        assertThat(testee.add(1, 2)).isEqualTo(3);
        assertThat(testee.add(-10, -10)).isEqualTo(-20);
        assertThat(testee.add(101, -202)).isEqualTo(-101);
    }

    @Test
    public void 引き算が正しく行われること() throws Exception {
        assertThat(testee.subtract(5, 3)).isEqualTo(2);
        assertThat(testee.subtract(-40, -40)).isEqualTo(0);
        assertThat(testee.subtract(250, -500)).isEqualTo(750);
    }

    @Test
    public void 同値比較が行われること() throws Exception {
        assertThat(testee.equals(123, 123)).isTrue();
        assertThat(testee.equals(123, 456)).isFalse();
    }
}
