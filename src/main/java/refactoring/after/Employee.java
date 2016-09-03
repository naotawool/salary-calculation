package refactoring.after;

public class Employee {

    protected Integer id;
    protected String name;
    protected EmployeeType type;

    public Employee(Integer id, String name, EmployeeType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long payAmount() {
        return type.payAmount(this);
    }

    public Long getMonthlySalary() {
        return 1000L; // DUMMY
    }

    public Long getCommission() {
        return 500L; // DUMMY
    }

    public Long getBonus() {
        return 300L; // DUMMY
    }
}
