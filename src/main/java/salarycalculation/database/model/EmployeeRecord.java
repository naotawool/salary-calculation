package salarycalculation.database.model;

import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 社員情報を保持する Entity。
 *
 * @author naotake
 */
public class EmployeeRecord {

    /** 社員番号 */
    private int no;

    /** 社員名 */
    private String name;

    /** 組織コード */
    private String organization;

    /** 生年月日 */
    private Date birthday;

    /** 入社年月日 */
    private Date joinDate;

    /** 役割等級 */
    private String roleRank;

    /** 能力等級 */
    private String capabilityRank;

    /** 通勤手当金額 */
    private int commuteAmount;

    /** 住宅手当金額 */
    private int rentAmount;

    /** 健康保険金額 */
    private int healthInsuranceAmount;

    /** 厚生年金金額 */
    private int employeePensionAmount;

    /** 所得税金額 */
    private int incomeTaxAmount;

    /** 住民税金額 */
    private int inhabitantTaxAmount;

    /** 1時間当たりの時間外手当金額 */
    private int workOverTime1hAmount;

    /**
     * 社員番号を取得する。
     *
     * @return 社員番号
     */
    public int getNo() {
        return no;
    }

    /**
     * 社員番号を設定する。
     *
     * @param no 社員番号
     */
    public void setNo(int no) {
        this.no = no;
    }

    /**
     * 社員名を取得する。
     *
     * @return 社員名
     */
    public String getName() {
        return name;
    }

    /**
     * 社員名を設定する。
     *
     * @param name 社員名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 組織コードを取得する。
     *
     * @return 組織コード
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * 組織コードを設定する。
     *
     * @param organization 組織コード
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * 生年月日を取得する。
     *
     * @return 生年月日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 生年月日を設定する。
     *
     * @param birthday 生年月日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 入社年月日を取得する。
     *
     * @return 入社年月日
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * 入社年月日を設定する。
     *
     * @param joinDate 入社年月日
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * 役割等級を取得する。
     *
     * @return 役割等級
     */
    public String getRoleRank() {
        return roleRank;
    }

    /**
     * 役割等級を設定する。
     *
     * @param roleRank 役割等級
     */
    public void setRoleRank(String roleRank) {
        this.roleRank = roleRank;
    }

    /**
     * 能力等級を取得する。
     *
     * @return 能力等級
     */
    public String getCapabilityRank() {
        return capabilityRank;
    }

    /**
     * 能力等級を設定する。
     *
     * @param capabilityRank 能力等級
     */
    public void setCapabilityRank(String capabilityRank) {
        this.capabilityRank = capabilityRank;
    }

    /**
     * 通勤手当金額を取得する。
     *
     * @return 通勤手当金額
     */
    public int getCommuteAmount() {
        return commuteAmount;
    }

    /**
     * 通勤手当金額を設定する。
     *
     * @param commuteAmount 通勤手当金額
     */
    public void setCommuteAmount(int commuteAmount) {
        this.commuteAmount = commuteAmount;
    }

    /**
     * 住宅手当金額を取得する。
     *
     * @return 住宅手当金額
     */
    public int getRentAmount() {
        return rentAmount;
    }

    /**
     * 住宅手当金額を設定する。
     *
     * @param rentAmount 住宅手当金額
     */
    public void setRentAmount(int rentAmount) {
        this.rentAmount = rentAmount;
    }

    /**
     * 健康保険金額を取得する。
     *
     * @return 健康保険金額
     */
    public int getHealthInsuranceAmount() {
        return healthInsuranceAmount;
    }

    /**
     * 健康保険金額を設定する。
     *
     * @param healthInsuranceAmount 健康保険金額
     */
    public void setHealthInsuranceAmount(int healthInsuranceAmount) {
        this.healthInsuranceAmount = healthInsuranceAmount;
    }

    /**
     * 厚生年金金額を取得する。
     *
     * @return 厚生年金金額
     */
    public int getEmployeePensionAmount() {
        return employeePensionAmount;
    }

    /**
     * 厚生年金金額を設定する。
     *
     * @param employeePensionAmount 厚生年金金額
     */
    public void setEmployeePensionAmount(int employeePensionAmount) {
        this.employeePensionAmount = employeePensionAmount;
    }

    /**
     * 所得税金額を取得する。
     *
     * @return 所得税金額
     */
    public int getIncomeTaxAmount() {
        return incomeTaxAmount;
    }

    /**
     * 所得税金額を設定する。
     *
     * @param incomeTaxAmount 所得税金額
     */
    public void setIncomeTaxAmount(int incomeTaxAmount) {
        this.incomeTaxAmount = incomeTaxAmount;
    }

    /**
     * 住民税金額を取得する。
     *
     * @return 住民税金額
     */
    public int getInhabitantTaxAmount() {
        return inhabitantTaxAmount;
    }

    /**
     * 住民税金額を設定する。
     *
     * @param inhabitantTaxAmount 住民税金額
     */
    public void setInhabitantTaxAmount(int inhabitantTaxAmount) {
        this.inhabitantTaxAmount = inhabitantTaxAmount;
    }

    /**
     * 1時間当たりの時間外手当金額を取得する。
     *
     * @return 1時間当たりの時間外手当金額
     */
    public int getWorkOverTime1hAmount() {
        return workOverTime1hAmount;
    }

    /**
     * 1時間当たりの時間外手当金額を設定する。
     *
     * @param workOverTime1hAmount 1時間当たりの時間外手当金額
     */
    public void setWorkOverTime1hAmount(int workOverTime1hAmount) {
        this.workOverTime1hAmount = workOverTime1hAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(no).append(name)
                .append(organization).append(birthday).append(joinDate).append(roleRank)
                .append(capabilityRank).append(commuteAmount).append(rentAmount)
                .append(healthInsuranceAmount).append(employeePensionAmount)
                .append(incomeTaxAmount).append(inhabitantTaxAmount).toString();
    }
}
