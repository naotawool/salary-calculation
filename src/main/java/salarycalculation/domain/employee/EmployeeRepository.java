package salarycalculation.domain.employee;

/**
 * リポジトリインタフェース.DIPを適用するためI/Fを作成.
 *
 * @author MASAYUKI
 *
 */
public interface EmployeeRepository {

    Employees findAllOrderByAnnualSalary(final boolean ascending);

    Employees findAll();

    Employee getSimple(String no);

    Employee get(String no);

    Employee getByDurationMonth(boolean selectMax);

    // 以下のメソッドはリポジトリに入れるか(永続化と復元の責務から外れている)際どいところ。Daoにあってもいいけど。
    // Entities(Employees)がいいのかも。
    long countByOrganization(String organizationCode);

}
