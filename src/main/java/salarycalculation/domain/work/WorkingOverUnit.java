package salarycalculation.domain.work;

import java.math.BigDecimal;

import salarycalculation.utils.BaseValueObject;

/**
 * 時間外労働を表すVO。
 *
 * @author MASAYUKI
 */
public class WorkingOverUnit extends BaseValueObject {

    /** 時間外労働時間 */
    private final BigDecimal workingTime;

    /** 時間外労働のタイプ */
    private final WorkingTimeType workingTimeType;

    public WorkingOverUnit(BigDecimal workingTime, WorkingTimeType workingTimeType) {
        this.workingTime = workingTime;
        this.workingTimeType = workingTimeType;
    }

    public BigDecimal getWorkingTime() {
        return workingTime;
    }

    public WorkingTimeType getWorkingTimeType() {
        return workingTimeType;
    }
}
