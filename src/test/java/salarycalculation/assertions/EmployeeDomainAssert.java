package salarycalculation.assertions;

import org.assertj.core.api.AbstractAssert;

import salarycalculation.domain.EmployeeDomain;

/**
 * {@link EmployeeDomain}の検証を行うためのカスタム Assertion クラス。
 *
 * @author naotake
 */
public class EmployeeDomainAssert extends AbstractAssert<EmployeeDomainAssert, EmployeeDomain> {

    protected EmployeeDomainAssert(EmployeeDomain actual) {
        super(actual, EmployeeDomainAssert.class);
    }
}
