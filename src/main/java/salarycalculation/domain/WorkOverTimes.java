package salarycalculation.domain;

import java.util.Map;
import java.util.Optional;

import salarycalculation.utils.BaseValueObject;

public class WorkOverTimes extends BaseValueObject {

    private final Map<Integer, WorkOverTime> yearMonthAttendanceTime;

    public WorkOverTimes(Map<Integer, WorkOverTime> yearMonthAttendanceTime) {
        this.yearMonthAttendanceTime = yearMonthAttendanceTime;
    }

    public Optional<WorkOverTime> getWorkOverTime(int yyyymm) {
        return Optional.ofNullable(yearMonthAttendanceTime.get(Integer.valueOf(yyyymm)));
    }

}
