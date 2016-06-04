package salarycalculation.database.repository;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;

import salarycalculation.database.WorkDao;
import salarycalculation.domain.work.WorkOverTime;
import salarycalculation.domain.work.WorkOverTimes;
import salarycalculation.domain.work.WorkRepository;
import salarycalculation.entity.WorkRecord;

public class WorkRepositoryDao implements WorkRepository {

    private WorkDao dao;

    public WorkRepositoryDao() {
        this.dao = new WorkDao();
    }

    @Override
    public Optional<WorkOverTimes> findByEmployeeId(int employeeNo) {

        List<WorkRecord> workRecords = dao.findAll(employeeNo);
        if (workRecords.isEmpty()) {
            return Optional.empty();
        }
        List<WorkOverTime> workOverTimeList = workRecords.stream()
                .map(this::convertWorkOverTime)
                .collect(toList());

        WorkOverTimes workOverTimes = new WorkOverTimes(workOverTimeList);
        return Optional.of(workOverTimes);
    }

    /**
     * 勤怠レコードを変換する
     *
     * @param record 勤怠の一レコード
     * @return 時間外勤務時間
     */
    private WorkOverTime convertWorkOverTime(WorkRecord record) {

        return WorkOverTime.builder(record.getWorkYearMonth(), record.getEmployeeNo())
                .holidayLateNightOverTime(record.getHolidayLateNightOverTime())
                .holidayWorkTime(record.getHolidayWorkTime())
                .lateNightOverTime(record.getLateNightOverTime())
                .workOverTime(record.getWorkOverTime())
                .build();
    }

}
