package unittest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link Amount}に対するテストクラス。
 *
 * @author naotake
 */
public class AmountTest {

    private Amount testee;

    @Before
    public void setUp() throws Exception {
        testee = Amount.of(1000, 80);
    }

    @Test
    public void 加算が正しく行われること() throws Exception {
        // 正値
        Amount actual = testee.add(Amount.of(500, 40));
        assertThat(actual).isEqualTo(Amount.of((1000 + 500), (80 + 40)));

        // 負数
        actual = testee.add(Amount.of(-500, -40));
        assertThat(actual).isEqualTo(Amount.of((1000 + -500), (80 + -40)));
    }

    @Test
    public void 減算が正しく行われること() throws Exception {
        // 正数
        Amount actual = testee.subtract(Amount.of(300, 24));
        assertThat(actual).isEqualTo(Amount.of((1000 - 300), (80 - 24)));

        // 負数
        actual = testee.subtract(Amount.of(-300, -24));
        assertThat(actual).isEqualTo(Amount.of((1000 - -300), (80 - -24)));
    }

    @Test
    public void 乗算が正しく行われること() throws Exception {
        // 正数
        Amount actual = testee.multiply(Amount.of(100, 8));
        assertThat(actual).isEqualTo(Amount.of((1000 * 100), (80 * 8)));

        // 負数
        actual = testee.multiply(Amount.of(-100, -8));
        assertThat(actual).isEqualTo(Amount.of((1000 * -100), (80 * -8)));
    }

    @Test
    public void 税抜金額と消費税から税込金額を取得できること() throws Exception {
        assertThat(testee.getPretax()).isEqualTo(1000 + 80);
    }

    @Test
    public void 税抜金額と消費税を使ってequalsが正しく動作こと() throws Exception {
        // 同額
        Amount actual = Amount.of(1000, 80);
        assertThat(actual).isEqualTo(testee);

        // 税抜金額が異なる
        actual = Amount.of(1001, 80);
        assertThat(actual).isNotEqualTo(testee);

        // 消費税が異なる
        actual = Amount.of(1000, 79);
        assertThat(actual).isNotEqualTo(testee);
    }

    @Test
    public void 期待する文字列表現を取得できること() throws Exception {
        assertThat(testee).hasToString("1,080円(税80円)");
    }
}
