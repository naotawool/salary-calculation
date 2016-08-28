package unittest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link Sample}に対するテストクラス。
 *
 * @author naotake
 */
public class SampleTest {

    private Sample testee;

    @Before
    public void setUp() throws Exception {
        testee = new Sample();
    }

    @Test
    public void testAdd() throws Exception {
        assertThat(testee.add(1, 2)).isEqualTo(3);
        assertThat(testee.add(-10, -10)).isEqualTo(-20);
        assertThat(testee.add(101, -202)).isEqualTo(-101);
    }

    @Test
    public void testSubtract() throws Exception {
        assertThat(testee.subtract(5, 3)).isEqualTo(2);
        assertThat(testee.subtract(-40, -40)).isEqualTo(0);
        assertThat(testee.subtract(250, -500)).isEqualTo(750);
    }
}
