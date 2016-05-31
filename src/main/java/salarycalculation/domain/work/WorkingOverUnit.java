package salarycalculation.domain.work;

import java.math.BigDecimal;

import salarycalculation.utils.BaseValueObject;

public class WorkingOverUnit extends BaseValueObject {

    private final BigDecimal workingTime;
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
