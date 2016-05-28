package salarycalculation.domain;

import java.util.List;

import salarycalculation.utils.Money;

/**
 *
 * @author MASAYUKI
 *
 */
public class Employees {

    private List<Employee> employees;

    public Employees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * 指定年月の全社員の手取り額の平均を取得する。
     *
     * @param yearMonth 算出対象の年月 (e.g. 201504)
     * @return 全社員の手取り額平均
     */
    // @UT
    public int getAverageTakeHome(int yearMonth) {

        return (int) employees.stream()
                .mapToInt(e -> e.getTakeHomeAmount(yearMonth).getAmount().intValue())
                .average()
                .getAsDouble();
    }

    /**
     * 想定年収が指定額を超えている社員数を取得する。
     *
     * @param condition 視定額
     * @return 該当する社員数
     */
    // @UT
    public int getCountByOverAnnualSalary(int condition) {
        Money assumption = Money.from(condition);
        return (int) employees.stream()
                .map(Employee::getAnnualTotalSalaryPlan)
                .filter(e -> e.isGraterThan(assumption))
                .count();
    }

    /**
     * 指定年月の全社員の総支給額の合計を取得する。
     *
     *
     * @param yearMonth 算出対象の年月 (e.g. 201504)
     * @return 全社員の総支給額合計
     */
    // @UT
    public int getSumTotalSalary(int yearMonth) {
        return employees.stream()
                .mapToInt(e -> e.getTotalSalary(yearMonth).getAmount().intValue())
                .sum();
    }

    /**
     * 指定された組織コードに所属する社員数を取得する。
     * TODO リポジトリと重複しているから悩ましいところ。
     *
     * @param organizationCode 組織コード
     * @return 所属する社員数
     */
    public long countByOrganization(String organizationCode) {
        return employees.stream().map(Employee::getOrganization).filter(e -> e.equalsName(organizationCode)).count();
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
