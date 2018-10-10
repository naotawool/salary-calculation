package unittest;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link Sample}に対する JUnit5 を使ったテストクラス。
 *
 * @author naotake
 */
class SampleJUnit5Test {

    private Sample testee;

    @BeforeEach
    void setUp() throws Exception {
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

    @Test
    @DisplayName("同値比較が行われること😆")
    public void testEquals() throws Exception {
        assertThat(testee.equals(123, 123)).isTrue();
        assertThat(testee.equals(123, 456)).isFalse();
    }
}
