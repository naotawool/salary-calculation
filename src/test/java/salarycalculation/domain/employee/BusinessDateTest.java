package salarycalculation.domain.employee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * {@link BusinessDate}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(JUnitParamsRunner.class)
public class BusinessDateTest {

    private BusinessDate testee;

    @Before
    public void setUp() throws Exception {
        testee = BusinessDate.of(2013, 4, 1);
    }

    @Test
    @Parameters(method = "parameters")
    public void 二つの日付の月数を取得できること(Fixture f) {
        int actual = testee.calculatePeriodByMonth(f.target);
        assertThat(actual).isEqualTo(f.expect);
    }

    @SuppressWarnings("unused") // パラメータとして動的に使用
    private List<Fixture> parameters() {
        Fixture f1 = new Fixture(BusinessDate.of(2014, 3, 31), 11);
        Fixture f2 = new Fixture(BusinessDate.of(2014, 4, 1), 12);
        Fixture f3 = new Fixture(BusinessDate.of(2014, 4, 2), 12);
        Fixture f4 = new Fixture(BusinessDate.of(2013, 4, 1), 0);
        Fixture f5 = new Fixture(BusinessDate.of(2013, 4, 30), 0);
        Fixture f6 = new Fixture(BusinessDate.of(2013, 5, 1), 1);

        return Arrays.asList(f1, f2, f3, f4, f5, f6);
    }

    @AllArgsConstructor
    @ToString
    private static final class Fixture {
        public BusinessDate target;
        public int expect;
    }
}
