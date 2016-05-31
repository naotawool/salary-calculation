package salarycalculation.assertions;

import org.assertj.core.api.JUnitSoftAssertions;

import salarycalculation.domain.employee.Employee;

/**
 * プロジェクト用に拡張した{@link JUnitSoftAssertions}のエントリポイント。
 *
 * @author naotake
 */
public class MyProjectJUnitSoftAssertions extends JUnitSoftAssertions {

    public EmployeeDomainAssert assertThat(Employee actual) {
        return proxy(EmployeeDomainAssert.class, Employee.class, actual);
    }
}
