package salarycalculation.web.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import salarycalculation.database.repository.EmployeeRepositoryDao;
import salarycalculation.domain.employee.Employee;
import salarycalculation.domain.employee.EmployeeRepository;
import salarycalculation.domain.employee.Employees;
import salarycalculation.web.representation.EmployeeView;

/**
 * 従業員に関するリクエストを受け付けるクラス。
 *
 * @author naotake
 */
@Path("/employee")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class EmployeeResource {

    private EmployeeRepository repository;

    public EmployeeResource() {
        repository = new EmployeeRepositoryDao();
    }

    @GET
    @Timed
    public EmployeeView get(@NotNull @QueryParam("no") String employeeNo) {
        Employee employee = repository.get(employeeNo);
        return new EmployeeView(employee.getId(), employee.getName());
    }

    @GET
    @Timed
    @Path("/list")
    public List<EmployeeView> list() {
        Employees employees = repository.findAll();
        return employees.getEmployees().stream().map(s -> new EmployeeView(s.getId(), s.getName()))
                        .collect(Collectors.toList());
    }
}
