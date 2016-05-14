package refactoring.after;

public class Engineer extends EmployeeType {

    @Override
    public Long payAmount(Employee employee) {
        return employee.getMonthlySalary();
    }
}
