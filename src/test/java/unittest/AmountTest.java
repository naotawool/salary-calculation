package unittest;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link Amount}に対するテストクラス。
 *
 * @author kyougoku.n
 */
public class AmountTest {

    private Amount testee;

    @Before
    public void setUp() throws Exception {
        testee = Amount.of(1000, 80);
    }

    @Test
    public void add_金額の加算が行われること() throws Exception {
        Amount augend = Amount.of(500, 40);

        // 実行
        Amount actual = testee.add(augend);

        // verify
        assertThat(actual.getNontax()).isEqualTo(1000 + 500);
        assertThat(actual.getTax()).isEqualTo(80 + 40);
        assertThat(actual.getPretax()).isEqualTo(1080 + 540);

        // これでもまぁ OK
        assertThat(testee.add(augend)).isEqualTo(Amount.of(1500, 120));
    }

    @Test
    public void subtract_金額の減算が行われること() throws Exception {
        Amount subtrahend = Amount.of(500, 40);

        // 実行
        Amount actual = testee.subtract(subtrahend);

        // verify
        assertThat(actual.getNontax()).isEqualTo(1000 - 500);
        assertThat(actual.getTax()).isEqualTo(80 - 40);
        assertThat(actual.getPretax()).isEqualTo(1080 - 540);
    }

    @Test
    public void multiply_金額の乗算が行われること() throws Exception {
        // 実行
        Amount actual = testee.multiply(3);

        // verify
        assertThat(actual.getNontax()).isEqualTo(1000 * 3);
        assertThat(actual.getTax()).isEqualTo(80 * 3);
        assertThat(actual.getPretax()).isEqualTo(1080 * 3);
    }

    @Test
    public void equals_金額の比較が行われること() throws Exception {
        // 金額が同じ場合
        Amount target = Amount.of(1000, 80);
        assertThat(testee.equals(target)).isTrue();

        // 金額が異なる場合
        target = Amount.of(1000, 81);
        assertThat(testee.equals(target)).isFalse();
    }

    @Test
    public void equals_異なる型のインスタンスを指定した場合にfalseになること() throws Exception {
        Sample target = new Sample();

        // 実行
        assertThat(testee.equals(target)).isFalse();
    }

    @Test
    public void toString_文字列表現を取得できること() throws Exception {
        assertThat(testee.toString()).isEqualTo("1,080円(税80円)");
        assertThat(testee).hasToString("1,080円(税80円)");
    }
}
