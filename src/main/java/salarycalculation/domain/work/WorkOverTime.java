package salarycalculation.domain.work;

import java.math.BigDecimal;

import salarycalculation.utils.BaseEntity;

public class WorkOverTime extends BaseEntity<WorkOverTimeId> {

    public static class Builder {

        /** 稼動年月 */
        private final int workYearMonth;

        /** 社員No */
        private int employeeNo;

        /** 時間外勤務時間 */
        private WorkingOverUnit workOverTime;

        /** 深夜勤務時間 */
        private WorkingOverUnit lateNightOverTime;

        /** 休日勤務時間 */
        private WorkingOverUnit holidayWorkTime;

        /** 休日深夜勤務時間 */
        private WorkingOverUnit holidayLateNightOverTime;

        private Builder(int workYearMonth, int employeeNo) {
            this.workYearMonth = workYearMonth;
            this.employeeNo = employeeNo;
        }

        public Builder workOverTime(BigDecimal lateNightOverTime) {
            this.workOverTime = new WorkingOverUnit(lateNightOverTime, WorkingTimeType.WORK_OVER);
            return this;
        }

        public Builder lateNightOverTime(BigDecimal lateNightOverTime) {
            this.lateNightOverTime = new WorkingOverUnit(lateNightOverTime, WorkingTimeType.LATE_NIGHT_OVER);
            return this;
        }

        public Builder holidayWorkTime(BigDecimal holidayWorkTime) {
            this.holidayWorkTime = new WorkingOverUnit(holidayWorkTime, WorkingTimeType.HOLIDAY_WORK);
            return this;
        }

        public Builder holidayLateNightOverTime(BigDecimal holidayLateNightOverTime) {
            this.holidayLateNightOverTime = new WorkingOverUnit(holidayLateNightOverTime,
                    WorkingTimeType.HOLIDAY_LATE_NIGHT_OVER);
            return this;
        }

        public WorkOverTime build() {

            return new WorkOverTime(
                    workYearMonth,
                    employeeNo,
                    workOverTime,
                    lateNightOverTime,
                    holidayWorkTime,
                    holidayLateNightOverTime);
        }

    }

    private final WorkOverTimeId id;

    /** 時間外勤務時間 */
    private final WorkingOverUnit workOverTime;

    /** 深夜勤務時間 */
    private final WorkingOverUnit lateNightOverTime;

    /** 休日勤務時間 */
    private final WorkingOverUnit holidayWorkTime;

    /** 休日深夜勤務時間 */
    private final WorkingOverUnit holidayLateNightOverTime;

    /**
     * use builder.
     *
     * @param workYearMonth
     * @param workOverTime
     * @param lateNightOverTime
     * @param holidayWorkTime
     * @param holidayLateNightOverTime
     */
    private WorkOverTime(int workYearMonth, int employeeNo, WorkingOverUnit workOverTime,
            WorkingOverUnit lateNightOverTime,
            WorkingOverUnit holidayWorkTime, WorkingOverUnit holidayLateNightOverTime) {
        super();
        this.id = new WorkOverTimeId(employeeNo, workYearMonth);
        this.workOverTime = workOverTime;
        this.lateNightOverTime = lateNightOverTime;
        this.holidayWorkTime = holidayWorkTime;
        this.holidayLateNightOverTime = holidayLateNightOverTime;
    }

    public static Builder builder(int workYearMonth, int employeeNo) {
        return new Builder(workYearMonth, employeeNo);
    }

    public WorkingOverUnit getWorkOverTime() {
        return workOverTime;
    }

    public WorkingOverUnit getLateNightOverTime() {
        return lateNightOverTime;
    }

    public WorkingOverUnit getHolidayWorkTime() {
        return holidayWorkTime;
    }

    public WorkingOverUnit getHolidayLateNightOverTime() {
        return holidayLateNightOverTime;
    }

    public BigDecimal getTotalWorkOverTime() {

        return this.workOverTime.getWorkingTime()
                .add(this.holidayLateNightOverTime.getWorkingTime())
                .add(this.holidayWorkTime.getWorkingTime())
                .add(this.lateNightOverTime.getWorkingTime());

    }

    @Override
    public WorkOverTimeId getId() {
        return this.id;
    }

}
