package refactoring.before;

public class Employee {

    protected Integer id;
    protected String name;
    protected String type;

    public Employee(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long payAmount() {
        switch (type) {
        case EmployeeType.ENGINEER:
            return getMonthlySalary();

        case EmployeeType.SALESMAN:
            return getMonthlySalary() + getCommission();

        case EmployeeType.MANAGER:
            return getMonthlySalary() + getBonus();

        default:
            throw new RuntimeException("不正な従業員");
        }
    }

    public Long getMonthlySalary() {
        return 1000L;
    }

    public Long getCommission() {
        return 500L;
    }

    public Long getBonus() {
        return 300L;
    }
}
