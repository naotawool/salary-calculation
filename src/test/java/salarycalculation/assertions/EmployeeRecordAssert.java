package salarycalculation.assertions;

import org.assertj.core.api.AbstractAssert;

import salarycalculation.database.model.EmployeeRecord;

/**
 * {@link EmployeeRecord}の検証を行うためのカスタム Assertion クラス。
 *
 * @author naotake
 */
public class EmployeeRecordAssert extends AbstractAssert<EmployeeRecordAssert, EmployeeRecord> {

    protected EmployeeRecordAssert(EmployeeRecord actual) {
        super(actual, EmployeeRecordAssert.class);
    }

    /**
     * {@link EmployeeRecord}を検証するためのカスタム Assertion クラス。
     *
     * @param actual 検証する値
     * @return {@link EmployeeRecordAssert}
     */
    public static EmployeeRecordAssert assertThat(EmployeeRecord actual) {
        return new EmployeeRecordAssert(actual);
    }

    public EmployeeRecordAssert hasNo(Integer no) {
        isNotNull();

        if (actual.getNo() != no) {
            failWithMessage("Expected employee's no to be <%s> but was <%s>", no, actual.getNo());
        }

        return this;
    }

    public EmployeeRecordAssert hasName(String name) {
        isNotNull();

        String assertErrorMessage = "Expected employee's name to be <%s> but was <%s>";
        if (!actual.getName().equals(name)) {
            failWithMessage(assertErrorMessage, name, actual.getName());
        }

        return this;
    }

    public EmployeeRecordAssert hasRoleRank(String rank) {
        isNotNull();

        String assertErrorMessage = "Expected employee's role rank to be <%s> but was <%s>";
        if (!actual.getRoleRank().equals(rank)) {
            failWithMessage(assertErrorMessage, rank, actual.getRoleRank());
        }

        return this;
    }

    public EmployeeRecordAssert hasCapabilityRank(String capability) {
        isNotNull();

        String assertErrorMessage = "Expected employee's capability rank to be <%s> but was <%s>";
        if (!actual.getCapabilityRank().equals(capability)) {
            failWithMessage(assertErrorMessage, capability, actual.getCapabilityRank());
        }

        return this;
    }

    public EmployeeRecordAssert hasOrganizationRank(String organization) {
        isNotNull();

        String assertErrorMessage = "Expected employee's organization to be <%s> but was <%s>";
        if (!actual.getOrganization().equals(organization)) {
            failWithMessage(assertErrorMessage, organization, actual.getOrganization());
        }

        return this;
    }
}
