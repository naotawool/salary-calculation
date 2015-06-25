package salarycalculation.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link EmployeeRepository}に対するテストクラス。
 *
 * @author naotake
 */
public class EmployeeRepositoryTest {

    private EmployeeRepository testee;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new EmployeeRepository();
    }

    @Test
    public void 社員番号_1_の情報を取得できること() {
        EmployeeDomain actual = testee.get("1");
        assertThat(actual.getNo(), is(1));
        assertThat(actual.getEntity().getName(), is("愛媛 蜜柑"));
        assertThat(actual.getEntity().getRoleRank(), is("A3"));
    }

    @Test
    public void 社員番号_2_の情報を取得できること() {
        EmployeeDomain actual = testee.get("2");
        assertThat(actual.getNo(), is(2));
        assertThat(actual.getEntity().getName(), is("大阪 太郎"));
        assertThat(actual.getEntity().getRoleRank(), is("C4"));
    }

    @Test
    public void 社員番号_3_の情報を取得できること() {
        EmployeeDomain actual = testee.get("3");
        assertThat(actual.getNo(), is(3));
        assertThat(actual.getEntity().getName(), is("埼玉 花子"));
        assertThat(actual.getEntity().getRoleRank(), is("M2"));
    }

    @Test
    public void 社員番号_4_の情報を取得できること() {
        EmployeeDomain actual = testee.get("4");
        assertThat(actual.getNo(), is(4));
        assertThat(actual.getEntity().getName(), is("東京 次郎"));
        assertThat(actual.getEntity().getRoleRank(), is("A1"));
    }
}
