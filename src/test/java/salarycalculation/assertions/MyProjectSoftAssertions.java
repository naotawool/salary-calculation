package salarycalculation.assertions;

import org.assertj.core.api.SoftAssertions;

import salarycalculation.domain.EmployeeDomain;

/**
 * プロジェクト用に拡張した{@link SoftAssertions}のエントリポイント。
 *
 * @author naotake
 */
public class MyProjectSoftAssertions extends SoftAssertions {

    public EmployeeDomainAssert assertThat(EmployeeDomain actual) {
        return proxy(EmployeeDomainAssert.class, EmployeeDomain.class, actual);
    }
}
