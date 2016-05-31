package salarycalculation.domain.work;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class WorkOverTimes {

    private final Map<Integer, WorkOverTime> yearMonthAttendanceTime;

    public WorkOverTimes(List<WorkOverTime> workOverTimes) {
        this.yearMonthAttendanceTime = workOverTimes.stream()
                .collect(toMap(e -> e.getId().getWorkYearMonth(), Function.identity()));
    }

    public Optional<WorkOverTime> getWorkOverTime(int yyyymm) {
        return Optional.ofNullable(yearMonthAttendanceTime.get(Integer.valueOf(yyyymm)));
    }

}
