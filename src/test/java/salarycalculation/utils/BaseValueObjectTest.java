package salarycalculation.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import lombok.ToString;

/**
 * {@link BaseValueObject}に対するテストクラス.
 *
 * @author naotake
 */
public class BaseValueObjectTest {

    private TestValueObject testee;
    private TestValueObject target;

    @Before
    public void setUp() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        testee = new TestValueObject();
        testee.foo = 123;
        testee.bar = "Bar";
        testee.baz = now;

        target = new TestValueObject();
        target.foo = 123;
        target.bar = "Bar";
        target.baz = now;
    }

    @Test
    public void プロパティを使ってequalsが行われること() {
        assertThat(testee == target).isFalse();
        assertThat(testee.equals(target)).isTrue();
    }

    @Test
    public void プロパティを使ってhashCodeが行われること() {
        assertThat(testee.hashCode()).isEqualTo(target.hashCode());
    }

    @Test
    public void プロパティを使ってtoStringが行われること() {
        assertThat(testee).hasToString(target.toString());
    }

    @ToString
    private static final class TestValueObject extends BaseValueObject {
        int foo;
        String bar;
        LocalDateTime baz;
    }
}
