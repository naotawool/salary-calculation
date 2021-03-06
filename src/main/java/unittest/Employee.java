package unittest;

import java.util.Objects;

/**
 * 従業員を表すクラス。
 *
 * @author naotake
 */
public class Employee {

    /** 従業員番号。 */
    private final int no;

    /** 氏名（名）。 */
    private final String firstName;

    /** 氏名（性）。 */
    private final String lastName;

    /** 所属部署。 */
    private Department department;

    public Employee(int no, String firstName, String lastName) {
        this.no = no;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getNo() {
        return no;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * 所属部署の判定を行う。
     *
     * @param target 判定対象
     * @return 対象と同じ部署の場合は true
     */
    public boolean isSameDepartment(Department target) {
        return this.department == target;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    @Override
    public boolean equals(Object other) {
        if (!Employee.class.isInstance(other)) {
            return false;
        }

        return (Employee.class.cast(other).getNo() == no);
    }

    @Override
    public String toString() {
        return String.format("[%05d]%s", no, String.format("%s %s", firstName, lastName));
    }

    /**
     * 部署を表す列挙型。
     *
     * @author naotake
     */
    public static enum Department {
        /** 開発部。 */
        DEVELOPMENT,
        /** 営業部。 */
        SALES,
        /** 経理部。 */
        FINANCE,
        /** 人事部。 */
        PERSONNEL;
    }
}
