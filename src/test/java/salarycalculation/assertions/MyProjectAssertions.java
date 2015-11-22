package salarycalculation.assertions;

import org.assertj.core.api.Assertions;

import salarycalculation.domain.EmployeeDomain;

public class MyProjectAssertions extends Assertions {

    /**
     * {@link EmployeeDomainAssert}を検証するためのカスタム Assertion クラス。
     *
     * @param actual 検証する値
     * @return {@link EmployeeDomainAssert}
     */
    public static EmployeeDomainAssert assertThat(EmployeeDomain actual) {
        return new EmployeeDomainAssert(actual);
    }
}
