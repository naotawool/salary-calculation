package salarycalculation.assertions;

import org.assertj.core.api.SoftAssertions;

import salarycalculation.domain.Employee;

/**
 * プロジェクト用に拡張した{@link SoftAssertions}のエントリポイント。
 *
 * @author naotake
 */
public class MyProjectSoftAssertions extends SoftAssertions {

    public EmployeeDomainAssert assertThat(Employee actual) {
        return proxy(EmployeeDomainAssert.class, Employee.class, actual);
    }
}
