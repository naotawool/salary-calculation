package salarycalculation.entity;

import java.math.BigDecimal;

/**
 * 社員の稼動情報を保持する Entity。
 *
 * @author naotake
 */
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

    /**
     * 社員番号を取得する。
     *
     * @return 社員番号
     */
    public int getEmployeeNo() {
        return employeeNo;
    }

    /**
     * 社員番号を設定する。
     *
     * @param employeeNo 社員番号
     */
    public void setEmployeeNo(int employeeNo) {
        this.employeeNo = employeeNo;
    }

    /**
     * 稼動年月を取得する。
     *
     * @return 稼動年月
     */
    public int getWorkYearMonth() {
        return workYearMonth;
    }

    /**
     * 稼動年月を設定する。
     *
     * @param workYearMonth 稼動年月
     */
    public void setWorkYearMonth(int workYearMonth) {
        this.workYearMonth = workYearMonth;
    }

    /**
     * 時間外勤務時間を取得する。
     *
     * @return 時間外勤務時間
     */
    public BigDecimal getWorkOverTime() {
        return workOverTime;
    }

    /**
     * 時間外勤務時間を設定する。
     *
     * @param workOverTime 時間外勤務時間
     */
    public void setWorkOverTime(BigDecimal workOverTime) {
        this.workOverTime = workOverTime;
    }

    /**
     * 深夜勤務時間を取得する。
     *
     * @return 深夜勤務時間
     */
    public BigDecimal getLateNightOverTime() {
        return lateNightOverTime;
    }

    /**
     * 深夜勤務時間を設定する。
     *
     * @param lateNightOverTime 深夜勤務時間
     */
    public void setLateNightOverTime(BigDecimal lateNightOverTime) {
        this.lateNightOverTime = lateNightOverTime;
    }

    /**
     * 休日勤務時間を取得する。
     *
     * @return 休日勤務時間
     */
    public BigDecimal getHolidayWorkTime() {
        return holidayWorkTime;
    }

    /**
     * 休日勤務時間を設定する。
     *
     * @param holidayWorkTime 休日勤務時間
     */
    public void setHolidayWorkTime(BigDecimal holidayWorkTime) {
        this.holidayWorkTime = holidayWorkTime;
    }

    /**
     * 休日深夜勤務時間を取得する。
     *
     * @return 休日深夜勤務時間
     */
    public BigDecimal getHolidayLateNightOverTime() {
        return holidayLateNightOverTime;
    }

    /**
     * 休日深夜勤務時間を設定する。
     *
     * @param holidayLateNightOverTime 休日深夜勤務時間
     */
    public void setHolidayLateNightOverTime(BigDecimal holidayLateNightOverTime) {
        this.holidayLateNightOverTime = holidayLateNightOverTime;
    }
}
