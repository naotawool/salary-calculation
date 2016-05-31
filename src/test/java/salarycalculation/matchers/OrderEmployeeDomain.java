package salarycalculation.matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import salarycalculation.domain.employee.Employee;

/**
 * {@link Employee}一覧の並び順を検証する Matcher。
 *
 * @author naotake
 */
public class OrderEmployeeDomain extends TypeSafeMatcher<List<Employee>> {

    private List<Integer> expected;

    private OrderEmployeeDomain(List<Integer> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(List<Employee> actuals) {
        return selectEmployeeNos(actuals).equals(expected);
    }

    private List<Integer> selectEmployeeNos(List<Employee> actuals) {
        return actuals.stream().map(Employee::getId).collect(Collectors.toList());
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }

    @Override
    protected void describeMismatchSafely(List<Employee> actuals,
            Description mismatchDescription) {
        mismatchDescription.appendValue(selectEmployeeNos(actuals));
    }

    /**
     * {@link Employee}一覧の並び順を検証する Matcher を生成する。<br />
     * 指定された社員番号の順番に、{@link Employee} 一覧がソートされていることを検証する。
     *
     * @param employeeNos 期待する社員番号の一覧
     * @return {@link OrderEmployeeDomain}
     */
    public static OrderEmployeeDomain orderNos(int... employeeNos) {
        List<Integer> expected = new ArrayList<>(employeeNos.length);
        for (int employeeNo : employeeNos) {
            expected.add(employeeNo);
        }

        return new OrderEmployeeDomain(expected);
    }

}
