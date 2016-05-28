package salarycalculation.exception;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link RecordNotFoundException}に対するテストクラス。
 *
 * @author naotake
 */
public class RecordNotFoundExceptionTest {

    private RecordNotFoundException testee;

    @Test
    public void コンストラクタで指定した情報を文字列表現として取得できること() {
        testee = new RecordNotFoundException(DummyEntity.class, "Key1", 234);

        assertThat(
                testee.toString(),
                is("RecordNotFoundException(salarycalculation.exception.RecordNotFoundExceptionTest.DummyEntity)[Key1, 234]"));
    }

    private static class DummyEntity {
        // NOP
    }
}
