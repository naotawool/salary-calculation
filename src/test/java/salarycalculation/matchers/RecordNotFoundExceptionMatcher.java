package salarycalculation.matchers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link RecordNotFoundException}の中身を検証する Matcher。
 *
 * @author naotake
 */
public class RecordNotFoundExceptionMatcher extends TypeSafeMatcher<RecordNotFoundException> {

    private Class<?> expectEntityClass;
    private Object expectKey;

    RecordNotFoundExceptionMatcher(Class<?> expectEntityClass, Object expectKey) {
        this.expectEntityClass = expectEntityClass;
        this.expectKey = expectKey;
    }

    @Override
    public void describeTo(Description description) {
        // NOP
    }

    @Override
    protected boolean matchesSafely(RecordNotFoundException actual) {
        assertTargetClass(actual);
        assertKey(actual);
        return true;
    }

    private void assertTargetClass(RecordNotFoundException actual) {
        if (expectEntityClass != null) {
            assertThat(actual.getTargetClass(), equalTo(expectEntityClass));
        }
    }

    private void assertKey(RecordNotFoundException actual) {
        if (expectKey != null) {
            assertThat(actual.getKey().length, is(1));
            assertThat(actual.getKey()[0], is(expectKey));
        }
    }

    /**
     * {@link RecordNotFoundException}にセットされた検索対象のエンティティ型を検証する Matcher を生成する。
     *
     * @param expectEntityClass 期待するエンティティ型
     * @return {@link RecordNotFoundExceptionMatcher}
     */
    public static RecordNotFoundExceptionMatcher isClass(Class<?> expectEntityClass) {
        return new RecordNotFoundExceptionMatcher(expectEntityClass, null);
    }

    /**
     * {@link RecordNotFoundException}にセットされた検索時の主キーを検証する Matcher を生成する。<br />
     * ここでは主キーが 1 つであることを前提とした検証を行う。
     *
     * @param expectKey 期待する検索キー
     * @return {@link RecordNotFoundExceptionMatcher}
     */
    public static RecordNotFoundExceptionMatcher isKey(Object expectKey) {
        return new RecordNotFoundExceptionMatcher(null, expectKey);
    }
}
