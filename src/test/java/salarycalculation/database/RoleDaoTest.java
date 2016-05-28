package salarycalculation.database;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.*;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.*;

import java.util.Arrays;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import salarycalculation.entity.RoleRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link RoleDao}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Enclosed.class)
public class RoleDaoTest {

    public static class JUnit4を使った場合 {

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
            expect.expect(isClass(RoleRecord.class));
            expect.expect(isKey("XX"));

            testee.get("XX");
        }
    }

    public static class AssertJを使った場合 {

        private RoleDao testee;

        /**
         * 事前処理。
         */
        @Before
        public void setUp() {
            testee = new RoleDao();
        }

        @Test
        public void 引数が_null_の場合_NullPointerException_が発生すること() {
            // when
            Throwable thrown = catchThrowable(() -> {
                testee.get(null);
            });

            // expect
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        public void 引数が空文字の場合_NullPointerException_が発生すること() {
            // when
            Throwable thrown = catchThrowable(() -> {
                testee.get("");
            });

            // expect
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        public void 引数が2桁より少ない場合_IllegalArgumentException_が発生すること() {
            // when
            Throwable thrown = catchThrowable(() -> {
                testee.get("A");
            });

            // expect
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                              .hasMessage("等級は 2 桁で指定してください[1]");
        }

        @Test
        public void 引数が2桁より大きい場合_IllegalArgumentException_が発生すること() {
            // when
            Throwable thrown = catchThrowable(() -> {
                testee.get("123");
            });

            // expect
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                              .hasMessage("等級は 2 桁で指定してください[3]");
        }

        @Test
        public void 存在しない等級を指定した場合に例外が送出されること() {
            // when
            Throwable thrown = catchThrowable(() -> {
                testee.get("XX");
            });

            // expect
            final TargetKeys keysXX = new TargetKeys("XX");
            assertThat(thrown).isInstanceOf(RecordNotFoundException.class).is(targetRole).is(keysXX);
        }

        private final Condition<Throwable> targetRole = new Condition<Throwable>("target class") {

            public boolean matches(Throwable actual) {
                RecordNotFoundException casted = RecordNotFoundException.class.cast(actual);
                return (casted.getTargetClass() == RoleRecord.class);
            }
        };

        private static final class TargetKeys extends Condition<Throwable> {

            private final Object[] expects;

            public TargetKeys(Object... expects) {
                as(Arrays.toString(expects));
                this.expects = expects;
            }

            public boolean matches(Throwable actual) {
                RecordNotFoundException casted = RecordNotFoundException.class.cast(actual);
                return Arrays.equals(casted.getKey(), expects);
            }
        }
    }
}
