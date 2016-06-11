package refactoring.after;

public class Manager extends EmployeeType {

    @Override
    public Long payAmount(Employee employee) {
        return employee.getMonthlySalary() + employee.getBonus();
    }
}
