package salarycalculation.assertions;

import org.assertj.core.api.JUnitSoftAssertions;

import salarycalculation.domain.EmployeeDomain;

/**
 * プロジェクト用に拡張した{@link JUnitSoftAssertions}のエントリポイント。
 *
 * @author naotake
 */
public class MyProjectJUnitSoftAssertions extends JUnitSoftAssertions {

    public EmployeeDomainAssert assertThat(EmployeeDomain actual) {
        return proxy(EmployeeDomainAssert.class, EmployeeDomain.class, actual);
    }
}
