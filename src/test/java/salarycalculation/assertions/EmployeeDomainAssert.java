package salarycalculation.assertions;

import org.assertj.core.api.AbstractAssert;

import salarycalculation.domain.employee.Capability;
import salarycalculation.domain.employee.Employee;
import salarycalculation.domain.employee.Role;
import salarycalculation.domain.organization.Organization;

/**
 * {@link Employee}の検証を行うためのカスタム Assertion クラス。
 *
 * @author naotake
 */
public class EmployeeDomainAssert extends AbstractAssert<EmployeeDomainAssert, Employee> {

    protected EmployeeDomainAssert(Employee actual) {
        super(actual, EmployeeDomainAssert.class);
    }

    /**
     * {@link EmployeeDomainAssert}を検証するためのカスタム Assertion クラス。
     *
     * @param actual 検証する値
     * @return {@link EmployeeDomainAssert}
     */
    public static EmployeeDomainAssert assertThat(Employee actual) {
        return new EmployeeDomainAssert(actual);
    }

    public EmployeeDomainAssert hasNo(Integer no) {
        isNotNull();

        if (actual.getId() != no) {
            failWithMessage("Expected employee's no to be <%s> but was <%s>", no, actual.getId());
        }

        return this;
    }

    public EmployeeDomainAssert hasName(String name) {
        isNotNull();

        String assertErrorMessage = "Expected employee's name to be <%s> but was <%s>";
        if (!actual.getName().getFullName().equals(name)) {
            failWithMessage(assertErrorMessage, name, actual.getName());
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

    public EmployeeDomainAssert hasCapabilityRank(String capability) {
        isNotNull();

        Capability actualCapability = actual.getCapability();
        assertNotNull(actualCapability, "capability");

        String assertErrorMessage = "Expected employee's capability rank to be <%s> but was <%s>";
        if (!actualCapability.getRank().name().equals(capability)) {
            failWithMessage(assertErrorMessage, capability, actualCapability.getRank());
        }

        return this;
    }

    public EmployeeDomainAssert hasOrganizationRank(String organization) {
        isNotNull();

        Organization actualOrganization = actual.getOrganization();
        assertNotNull(actualOrganization, "organization");

        String assertErrorMessage = "Expected employee's organization to be <%s> but was <%s>";
        if (!actualOrganization.getName().equals(organization)) {
            failWithMessage(assertErrorMessage, organization, actualOrganization.getName());
        }

        return this;
    }

    private void assertNotNull(Object entity, String name) {
        if (entity == null) {
            failWithMessage("Employee %s to be null", name);
        }
    }
}
