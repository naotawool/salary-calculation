package unittest;

import java.time.LocalDate;
import java.time.Period;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 従業員を表すクラス。
 *
 * @author naotake
 */
public class Employee {

    /** 従業員番号。 */
    private final int no;

    /** 氏名（名）。 */
    private String firstName;

    /** 氏名（性）。 */
    private String lastName;

    /** 生年月日。 */
    private LocalDate birthday;

    /** 所属部署。 */
    private Department department;

    public Employee(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        // System.out.println(firstName + lastName);
        return String.format("%s %s", firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * 基準日時点での年齢を計算する。
     *
     * @param base 年齢計算で使用する基準日
     * @return 基準日時点での年齢
     */
    public int calcAge(LocalDate base) {
        return Period.between(birthday, base).getYears();
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
        return HashCodeBuilder.reflectionHashCode(this);
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
        return String.format("[%05d]%s", no, getName());
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
