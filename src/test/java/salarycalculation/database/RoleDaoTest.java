package salarycalculation.database;

import static org.hamcrest.core.Is.is;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKey;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import salarycalculation.entity.Role;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link RoleDao}に対するテストクラス。
 *
 * @author naotake
 */
public class RoleDaoTest {

    private RoleDao testee;

    @Rule
    public ExpectedException expect = ExpectedException.none();

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
        expect.expect(IllegalArgumentException.class);
        expect.expectMessage(is("等級は 2 桁で指定してください[1]"));

        testee.get("A");
    }

    @Test
    public void 引数が2桁より大きい場合_IllegalArgumentException_が発生すること() {
        expect.expect(IllegalArgumentException.class);
        expect.expectMessage(is("等級は 2 桁で指定してください[3]"));

        testee.get("123");
    }

    @Test
    public void 存在しない等級を指定した場合に例外が送出されること() {
        expect.expect(RecordNotFoundException.class);
        expect.expect(isClass(Role.class));
        expect.expect(isKey("XX"));

        testee.get("XX");
    }
}
