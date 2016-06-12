package salarycalculation.database.model;

import java.sql.Date;

import lombok.Data;

/**
 * 社員情報を保持する Entity。
 *
 * @author naotake
 */
@Data
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
}
