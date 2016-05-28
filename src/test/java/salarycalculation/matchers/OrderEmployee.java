package salarycalculation.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import salarycalculation.entity.EmployeeRecord;

/**
 * {@link EmployeeRecord}一覧の並び順を検証する Matcher。
 *
 * @author naotake
 */
public class OrderEmployee extends TypeSafeMatcher<List<EmployeeRecord>> {

    private List<Integer> expected;

    private OrderEmployee(List<Integer> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(List<EmployeeRecord> actuals) {
        return selectEmployeeNos(actuals).equals(expected);
    }

    private List<Integer> selectEmployeeNos(List<EmployeeRecord> actuals) {
        return actuals.stream().map(s -> s.getNo()).collect(Collectors.toList());
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }

    @Override
    protected void describeMismatchSafely(List<EmployeeRecord> actuals, Description mismatchDescription) {
        mismatchDescription.appendValue(selectEmployeeNos(actuals));
    }

    /**
     * {@link EmployeeRecord}一覧の並び順を検証する Matcher を生成する。<br />
     * 指定された社員番号の順番に、{@link EmployeeRecord} 一覧がソートされていることを検証する。
     *
     * @param employeeNos 期待する社員番号の一覧
     * @return {@link OrderEmployee}
     */
    public static OrderEmployee orderNos(int... employeeNos) {
        List<Integer> expected = new ArrayList<>(employeeNos.length);
        for (int employeeNo : employeeNos) {
            expected.add(employeeNo);
        }

        return new OrderEmployee(expected);
    }
}
