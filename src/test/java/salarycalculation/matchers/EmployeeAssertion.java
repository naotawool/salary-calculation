package salarycalculation.matchers;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import salarycalculation.database.model.EmployeeRecord;

/**
 * {@link EmployeeRecord}に対する検証をまとめたカスタム Matcher。
 *
 * @author naotake
 */
public class EmployeeAssertion extends TypeSafeMatcher<EmployeeRecord> {

    private Assertions assertions;
    private Object expected;

    private EmployeeAssertion(Assertions assertions, Object expected) {
        this.assertions = assertions;
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(EmployeeRecord actual) {
        return assertions.equals(actual, this.expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(assertions + " is " + expected);
    }

    @Override
    protected void describeMismatchSafely(EmployeeRecord actual, Description mismatchDescription) {
        mismatchDescription.appendValue(assertions + " is " + assertions.resolveActual(actual));
    }

    /**
     * {@link EmployeeRecord}の社員番号を検証する Matcher を生成する。
     *
     * @param expected 期待する社員番号
     * @return {@link EmployeeAssertion}
     */
    public static EmployeeAssertion isEqualToNo(int expected) {
        return new EmployeeAssertion(Assertions.NO, expected);
    }

    /**
     * {@link EmployeeRecord}の社員名を検証する Matcher を生成する。
     *
     * @param expected 期待する社員名
     * @return {@link EmployeeAssertion}
     */
    public static EmployeeAssertion isEqualToName(String expected) {
        return new EmployeeAssertion(Assertions.NAME, expected);
    }

    /**
     * 検証する値を表す列挙値。
     *
     * @author naotake
     */
    private static enum Assertions {

        /**
         * 社員番号
         */
        NO("No") {
            @Override
            boolean equals(EmployeeRecord actual, Object expected) {
                return actual.getNo() == (int) expected;
            }

            @Override
            Object resolveActual(EmployeeRecord actual) {
                return actual.getNo();
            }
        },

        /**
         * 社員名
         */
        NAME("Name") {
            @Override
            boolean equals(EmployeeRecord actual, Object expected) {
                return StringUtils.equals(actual.getName(), (String) expected);
            }

            @Override
            Object resolveActual(EmployeeRecord actual) {
                return actual.getName();
            }
        };

        private String fieldName;

        private Assertions(String fieldName) {
            this.fieldName = fieldName;
        }

        abstract boolean equals(EmployeeRecord actual, Object expected);

        abstract Object resolveActual(EmployeeRecord actual);

        public String toString() {
            return fieldName;
        }
    }
}
