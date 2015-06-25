package salarycalculation.database;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link RoleDao}に対するテストクラス。
 *
 * @author naotake
 */
public class RoleDaoTest {

    private RoleDao testee;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new RoleDao();
    }

    @Test(expected = NullPointerException.class)
    public void 引数が_null_の場合_NullPointerException_が発生すること() {
        testee.get(null);
    }

    @Test(expected = NullPointerException.class)
    public void 引数が空文字の場合_NullPointerException_が発生すること() {
        testee.get("");
    }

    @Test
    public void 引数が2桁より少ない場合_IllegalArgumentException_が発生すること() {
        try {
            testee.get("A");
            fail("ココまでこないはず");
        } catch (IllegalArgumentException e) {
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
            assertThat(e.getMessage(), is("等級は 2 桁で指定してください[1]"));
        }
    }

    @Test
    public void 引数が2桁より大きい場合_IllegalArgumentException_が発生すること() {
        try {
            testee.get("123");
            fail("ココまでこないはず");
        } catch (IllegalArgumentException e) {
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
            assertThat(e.getMessage(), is("等級は 2 桁で指定してください[3]"));
        }
    }
}
