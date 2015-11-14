package salarycalculation.exception;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * {@link RuntimeSQLException}に対するテストクラス。
 *
 * @author naotake
 */
public class RuntimeSQLExceptionTest {

    @Rule
    public ExpectedException expect = ExpectedException.none();

    @Test
    public void インスタンス生成が正しく行われること() {
        String expectMessage = "例外発生！";
        Exception expectException = new NullPointerException();

        expect.expectMessage(is(expectMessage));
        expect.expectCause(is(expectException));
        expect.expect(RuntimeSQLException.class);

        throw new RuntimeSQLException(expectMessage, expectException);
    }
}
