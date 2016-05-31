package salarycalculation.domain.employee;

import java.util.Optional;

import salarycalculation.domain.work.WorkOverTime;
import salarycalculation.domain.work.WorkOverTimeSalaryCalculator;
import salarycalculation.domain.work.WorkOverTimes;
import salarycalculation.utils.BaseEntity;
import salarycalculation.utils.Money;
import salarycalculation.utils.PersonName;

/**
 * 社員情報ドメイン。
 *
 * @author naotake
 */
public class Employee extends BaseEntity<Integer> {

    private final Integer employeeNo;

    /** 社員名 */
    private PersonName name;

    /** 組織情報 */
    private Organization organization;

    /** 役割等級 */
    private Role role;

    /** 能力等級 */
    private Capability capability;

    /** 生年月日 */
    private BusinessDate birthDay;

    /** 入社年月日 */
    private BusinessDate joinDate;

    /** 通勤手当金額　*/
    private Money commuteAmount;

    /** 住宅手当金額 */
    private Money rentAmount;

    /** 健康保険金額 */
    private Money healthInsuranceAmount;

    /** 厚生年金金額 */
    private Money employeePensionAmount;

    /** 所得税金額 */
    private Money incomeTaxAmount;

    /** 住民税金額 */
    private Money inhabitantTaxAmount;

    /** 1時間当たりの時間外手当金額 */
    private Money amountOverTimePerHour;

    private WorkOverTimes workOverTimes;

    public Employee(int employeeNo) {
        this.employeeNo = employeeNo;
    }

    /**
     * 入社年月日を基に勤続年数を取得する。<br />
     * 勤続年数が 1年未満の場合は 0 を返す。
     *
     * @return 勤続年数
     */
    // @UT
    public int getDurationYear(BusinessDate targetDate) {
        int durationMonth = calculateAttendanceMonth(targetDate);
        return (durationMonth / 12);
    }

    /**
     * 入社年月日を基に勤続月数を取得する。<br />
     * ここでいう勤続月数とは、現在日時点で勤続<b>何ヶ月目</b>かを表す。
     *
     * <pre>
     * 例えば
     * ・入社年月日: 2013/04/01
     * ・現在日時: 2014/04/01
     *
     * この場合、勤続月数は 13ヶ月目となる。
     *
     * ・現在日時: 2014/03/01 や 2014/03/31
     *
     * この場合、勤続月数は 12ヶ月目となる。
     * </pre>
     *
     * @return 勤続月数
     */
    // @UT
    public int calculateAttendanceMonth(BusinessDate targetDate) {
        int periodByMonth = joinDate.calculatePeriodByMonth(targetDate);
        // 勤続月数は二つの業務日付の差から１を足したもの
        return periodByMonth + 1;
    }

    /**
     * 指定年月の給料の手取り額を取得する。
     *
     * @param workYearMonth 稼動年月 (e.g. 201504)
     * @return 給料の手取り額
     */
    // @UT
    public Money getTakeHomeAmount(int workYearMonth) {
        // 総支給額を求める
        Money totalSalary = getTotalSalary(workYearMonth);

        // 控除額を求める
        Money deduction = healthInsuranceAmount
                .add(employeePensionAmount)
                .add(incomeTaxAmount)
                .add(inhabitantTaxAmount);

        // 差引給与額を求める
        Money takeHome = totalSalary.minus(deduction);

        return takeHome;
    }

    /**
     * 指定年月の給料の総支給額を取得する。<br />
     * 総支給額の内訳は下記の通り。
     * <p />
     * 総支給額 = 基準内給与 (基本給 + 諸手当) + 基準外給与
     *
     * @param workYearMonth 稼動年月 (e.g. 201504)
     * @return 給料の総支給額
     */
    // @UT
    public Money getTotalSalary(int workYearMonth) {
        return role.getAmount()
                .add(capability.getAmount())
                .add(getAllowance(BusinessDate.of(workYearMonth / 100, workYearMonth % 100, 1)))
                .add(getOvertimeAmount(workYearMonth));
    }

    /**
     * 現在時点での諸手当を取得する。
     *
     * @return 諸手当
     */
    public Money getAllowance(BusinessDate targetDate) {
        // 諸手当を求める

        Money totalAllowance = commuteAmount
                .add(rentAmount)
                .add(capability.getSeparatedAllowance())
                // 勤続手当の取得
                .add(LongServiceAllowance.targetAllowanance(calculateAttendanceMonth(targetDate)).allowance());

        return totalAllowance;

    }

    /**
     * 指定年月の残業代を取得する。
     *
     * @param workYearMonth 稼動年月 (e.g. 201504)
     * @return 残業代
     */
    // @UT
    public Money getOvertimeAmount(int workYearMonth) {

        // マネージャ職は残業代なし
        if (capability.isManager()) {
            return Money.ZERO;

        }

        // 稼動情報を取得
        Optional<WorkOverTime> workOverTimeOpt = workOverTimes.getWorkOverTime(workYearMonth);

        return workOverTimeOpt.map(workOverTime -> WorkOverTimeSalaryCalculator.create(amountOverTimePerHour)
                // 時間外手当
                .append(workOverTime.getWorkOverTime())
                // 深夜手当
                .append(workOverTime.getLateNightOverTime())
                // 休日手当
                .append(workOverTime.getHolidayWorkTime())
                // 休日深夜手当
                .append(workOverTime.getHolidayLateNightOverTime())
                .calculate())
                // 取得できなかった時は０円
                .orElse(Money.ZERO);

    }

    /**
     * 現在の各等級を基に想定年収を取得する。<br />
     * 想定年収の内訳は下記の通り。
     * <p />
     * 想定年収 = 基準内給与 (基本給 + 諸手当) * 12
     * <p />
     * ただし、以下の諸手当は年収には含まれない。
     * <ul>
     * <li>通勤手当</li>
     * <li>住宅手当</li>
     * <li>勤続手当</li>
     * </ul>
     *
     * @return 想定年収
     */
    // @UT
    public Money getAnnualTotalSalaryPlan() {
        // 想定年収を求める
        return calculateSaralyPerMonth().multiply(12);
    }

    private Money calculateSaralyPerMonth() {
        // 基本給を求める
        return role.getAmount()
                // 能力手当を足す
                .add(capability.getAmount())
                // 別途手当を足す
                .add(capability.getSeparatedAllowance());

    }

    @Override
    public Integer getId() {
        return this.employeeNo;
    }

    public PersonName getName() {
        return this.name;
    }

    public Role getRole() {
        return this.role;
    }

    public Capability getCapability() {
        return this.capability;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setCommuteAmount(Money commuteAmount) {
        this.commuteAmount = commuteAmount;

    }

    public void setRentAmount(Money rentAmount) {
        this.rentAmount = rentAmount;
    }

    public void setCapability(Capability capability) {
        this.capability = capability;
    }

    public void setJoinDate(BusinessDate joinDate) {
        this.joinDate = joinDate;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BusinessDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(BusinessDate birthDay) {
        this.birthDay = birthDay;
    }

    public Money getHealthInsuranceAmount() {
        return healthInsuranceAmount;
    }

    public void setHealthInsuranceAmount(Money healthInsuranceAmount) {
        this.healthInsuranceAmount = healthInsuranceAmount;
    }

    public Money getEmployeePensionAmount() {
        return employeePensionAmount;
    }

    public void setEmployeePensionAmount(Money employeePensionAmount) {
        this.employeePensionAmount = employeePensionAmount;
    }

    public Money getIncomeTaxAmount() {
        return incomeTaxAmount;
    }

    public void setIncomeTaxAmount(Money incomeTaxAmount) {
        this.incomeTaxAmount = incomeTaxAmount;
    }

    public Money getInhabitantTaxAmount() {
        return inhabitantTaxAmount;
    }

    public void setInhabitantTaxAmount(Money inhabitantTaxAmount) {
        this.inhabitantTaxAmount = inhabitantTaxAmount;
    }

    public Money getWorkOverTime1hAmount() {
        return amountOverTimePerHour;
    }

    public void setWorkOverTime1hAmount(Money workOverTime1hAmount) {
        this.amountOverTimePerHour = workOverTime1hAmount;
    }

    public WorkOverTimes getWorkTimes() {
        return workOverTimes;
    }

    public void setWorkTimes(WorkOverTimes workTimes) {
        this.workOverTimes = workTimes;
    }

    public BusinessDate getJoinDate() {
        return joinDate;
    }

    public Money getCommuteAmount() {
        return commuteAmount;
    }

    public Money getRentAmount() {
        return rentAmount;
    }

    public void setName(PersonName name) {
        this.name = name;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
