package salarycalculation.database.repository;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import salarycalculation.database.CapabilityDao;
import salarycalculation.database.OrganizationDao;
import salarycalculation.database.RoleDao;
import salarycalculation.database.WorkDao;
import salarycalculation.domain.BusinessDate;
import salarycalculation.domain.Capability;
import salarycalculation.domain.CapabilityRank;
import salarycalculation.domain.Employee;
import salarycalculation.domain.Organization;
import salarycalculation.domain.Role;
import salarycalculation.domain.WorkOverTime;
import salarycalculation.domain.WorkOverTimes;
import salarycalculation.entity.CapabilityRecord;
import salarycalculation.entity.EmployeeRecord;
import salarycalculation.entity.OrganizationRecord;
import salarycalculation.entity.RoleRecord;
import salarycalculation.entity.WorkRecord;
import salarycalculation.utils.Money;
import salarycalculation.utils.PersonName;

/**
 * employeeEntityとデータ構造の差を埋めるためのトランスフォーマ.<br />
 * リポジトリ実装のユーティリティに属する（DDDではない）
 *
 * @author MASAYUKI
 *
 */
public class EmployeeTransformer {

    private OrganizationDao organizationDao;
    private RoleDao roleDao;
    private CapabilityDao capabilityDao;
    private WorkDao workDao;

    public EmployeeTransformer() {
        this.organizationDao = new OrganizationDao();
        this.roleDao = new RoleDao();
        this.capabilityDao = new CapabilityDao();
        this.workDao = new WorkDao();
    }

    /**
     * 従業員レコードをEntityに変換する。
     *
     * @param employeeRecord
     * @return
     */
    public Employee transformToEntity(EmployeeRecord employeeRecord) {

        // 所属する組織情報を取得
        OrganizationRecord organization = organizationDao.get(employeeRecord.getOrganization());

        // 各等級情報を取得
        RoleRecord role = roleDao.get(employeeRecord.getRoleRank());
        CapabilityRecord capability = capabilityDao.get(employeeRecord.getCapabilityRank());

        //      TODO employeeはAggregateだけど、このタイミング全部持ってくるとパフォーマンスとメモリに影響でそうだからアーキテクチャを検討する必要があるかも。
        //       ex1. JPAみたいにLazyロードを検討する => FWないと実装が大変かも
        //       ex2. DomainServiceに処理を移す => 現実的な落とし所かも
        //       ex3. WorkRepositoryを作りEntityにWorkRepositoryを関数の引数に渡して取得する、
        //       EntityにRepositoryを渡すと依存が分かりづらくなるからなんとも。。。
        List<WorkRecord> workRecord = workDao.findAll(employeeRecord.getNo());

        return createFromRecord(employeeRecord, organization, workRecord, Optional.ofNullable(role),
                Optional.ofNullable(capability));

    }

    /**
     * DBレコードからEntityを生成する
     *
     * @param employeeRecord 従業員レコード
     * @param organizationRecord 組織レコード（オプション）
     * @param roleRecordOpt 役割等級（オプション）
     * @param capabilityRecordOpt 能力等級レコード（オプション）
     * @return 従業員エンティティ
     */
    public Employee createFromRecord(EmployeeRecord employeeRecord, OrganizationRecord organizationRecord,
            List<WorkRecord> workRecords,
            Optional<RoleRecord> roleRecordOpt,
            Optional<CapabilityRecord> capabilityRecordOpt) {

        Employee entity = new Employee(employeeRecord.getNo());

        Map<Integer, WorkOverTime> yearMonthAttendanceTime = workRecords.size() > 0 ? workRecords.stream()
                .map(this::convertWorkOverTime)
                .collect(toMap(e -> Integer.valueOf(e.getWorkYearMonth()), Function.identity()))
                : Collections.emptyMap();

        entity.setName(new PersonName(employeeRecord.getName()));
        entity.setBirthDay(BusinessDate.of(employeeRecord.getBirthday()));
        entity.setJoinDate(BusinessDate.of(employeeRecord.getJoinDate()));
        entity.setOrganization(new Organization(organizationRecord.getCode(), organizationRecord.getName()));

        Optional<Capability> capabilityOpt = capabilityRecordOpt
                .map(e -> Capability.normal(CapabilityRank.valueOf(e.getRank()), Money.from(e.getAmount())));
        if (capabilityOpt.isPresent()) {
            entity.setCapability(capabilityOpt.get());
        }

        Optional<Role> roleOpt = roleRecordOpt.map(e -> new Role(e.getRank(), Money.from(e.getAmount())));
        if (roleOpt.isPresent()) {
            entity.setRole(roleOpt.get());
        }

        entity.setCommuteAmount(Money.from(employeeRecord.getCommuteAmount()));
        entity.setEmployeePensionAmount(Money.from(employeeRecord.getEmployeePensionAmount()));
        entity.setHealthInsuranceAmount(Money.from(employeeRecord.getHealthInsuranceAmount()));
        entity.setIncomeTaxAmount(Money.from(employeeRecord.getIncomeTaxAmount()));
        entity.setInhabitantTaxAmount(Money.from(employeeRecord.getInhabitantTaxAmount()));
        entity.setRentAmount(Money.from(employeeRecord.getRentAmount()));

        entity.setWorkOverTime1hAmount(Money.from(employeeRecord.getWorkOverTime1hAmount()));

        entity.setWorkTimes(new WorkOverTimes(yearMonthAttendanceTime));
        return entity;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setCapabilityDao(CapabilityDao capabilityDao) {
        this.capabilityDao = capabilityDao;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    public void setWorkDao(WorkDao workDao) {
        this.workDao = workDao;
    }

    /**
     * 勤怠レコードを変換する
     *
     * @param record 勤怠の一レコード
     * @return 時間外勤務時間
     */
    private WorkOverTime convertWorkOverTime(WorkRecord record) {

        return WorkOverTime.builder(record.getWorkYearMonth())
                .holidayLateNightOverTime(record.getHolidayLateNightOverTime())
                .holidayWorkTime(record.getHolidayWorkTime())
                .lateNightOverTime(record.getLateNightOverTime())
                .workOverTime(record.getWorkOverTime())
                .build();
    }
}
