package salarycalculation.assertions;

import org.assertj.core.api.AbstractAssert;

import salarycalculation.domain.EmployeeDomain;
import salarycalculation.entity.Capability;
import salarycalculation.entity.Employee;
import salarycalculation.entity.Organization;
import salarycalculation.entity.Role;

/**
 * {@link EmployeeDomain}の検証を行うためのカスタム Assertion クラス。
 *
 * @author naotake
 */
public class EmployeeDomainAssert extends AbstractAssert<EmployeeDomainAssert, EmployeeDomain> {

    protected EmployeeDomainAssert(EmployeeDomain actual) {
        super(actual, EmployeeDomainAssert.class);
    }

    /**
     * {@link EmployeeDomainAssert}を検証するためのカスタム Assertion クラス。
     *
     * @param actual 検証する値
     * @return {@link EmployeeDomainAssert}
     */
    public static EmployeeDomainAssert assertThat(EmployeeDomain actual) {
        return new EmployeeDomainAssert(actual);
    }

    public EmployeeDomainAssert hasNo(Integer no) {
        isNotNull();

        if (actual.getNo() != no) {
            failWithMessage("Expected employee's no to be <%s> but was <%s>", no, actual.getNo());
        }

        return this;
    }

    public EmployeeDomainAssert hasName(String name) {
        isNotNull();

        Employee actualEntity = actual.getEntity();
        assertNotNull(actualEntity, "entity");

        String assertErrorMessage = "Expected employee's name to be <%s> but was <%s>";
        if (!actualEntity.getName().equals(name)) {
            failWithMessage(assertErrorMessage, name, actualEntity.getName());
        }

        return this;
    }

    public EmployeeDomainAssert hasRoleRank(String rank) {
        isNotNull();

        Role actualRole = actual.getRole();
        assertNotNull(actualRole, "role");

        String assertErrorMessage = "Expected employee's role rank to be <%s> but was <%s>";
        if (!actualRole.getRank().equals(rank)) {
            failWithMessage(assertErrorMessage, rank, actualRole.getRank());
        }

        return this;
    }

    public EmployeeDomainAssert hasCapabilityRank(String rank) {
        isNotNull();

        Capability actualCapability = actual.getCapability();
        assertNotNull(actualCapability, "capability");

        String assertErrorMessage = "Expected employee's capability rank to be <%s> but was <%s>";
        if (!actualCapability.getRank().equals(rank)) {
            failWithMessage(assertErrorMessage, rank, actualCapability.getRank());
        }

        return this;
    }

    public EmployeeDomainAssert hasOrganizationRank(String name) {
        isNotNull();

        Organization actualOrganization = actual.getOrganization();
        assertNotNull(actualOrganization, "organization");

        String assertErrorMessage = "Expected employee's organization to be <%s> but was <%s>";
        if (!actualOrganization.getName().equals(name)) {
            failWithMessage(assertErrorMessage, name, actualOrganization.getName());
        }

        return this;
    }

    private void assertNotNull(Object entity, String name) {
        if (entity == null) {
            failWithMessage("Employee %s to be null", name);
        }
    }
}
