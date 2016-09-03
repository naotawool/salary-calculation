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

    private final int no;
    private String firstName;
    private String lastName;
    private final LocalDate birthday;
    private Department department;

    public Employee(int no, LocalDate birthday) {
        this.no = no;
        this.birthday = birthday;
    }

    public int getNo() {
        return no;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * 現在日時点での年齢を計算する。
     *
     * @return 現在日時点での年齢
     */
    public int calcAge() {
        return calcAge(LocalDate.now());
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
