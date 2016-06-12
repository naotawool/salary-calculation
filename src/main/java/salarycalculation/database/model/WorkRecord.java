package salarycalculation.database.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 社員の稼動情報を保持する Entity。
 *
 * @author naotake
 */
@Data
public class WorkRecord {

    /** 社員番号 */
    private int employeeNo;

    /** 稼動年月 */
    private int workYearMonth;

    /** 時間外勤務時間 */
    private BigDecimal workOverTime;

    /** 深夜勤務時間 */
    private BigDecimal lateNightOverTime;

    /** 休日勤務時間 */
    private BigDecimal holidayWorkTime;

    /** 休日深夜勤務時間 */
    private BigDecimal holidayLateNightOverTime;
}
