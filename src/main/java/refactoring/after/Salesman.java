package refactoring.after;

public class Salesman extends EmployeeType {

    @Override
    public Long payAmount(Employee employee) {
        return employee.getMonthlySalary() + employee.getCommission();
    }
}
