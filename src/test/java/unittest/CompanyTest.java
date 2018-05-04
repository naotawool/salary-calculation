package unittest;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import unittest.Employee.Department;

/**
 * {@link Company}に対するテストクラス。
 *
 * @author naotake
 */
public class CompanyTest {

    private Company testee;

    @Before
    public void setUp() throws Exception {
        Employee emp1 = new Employee(1);
        emp1.setDepartment(Department.DEVELOPMENT);
        Employee emp2 = new Employee(2);
        emp2.setDepartment(Department.FINANCE);
        Employee emp3 = new Employee(3);
        emp3.setDepartment(Department.PERSONNEL);
        Employee emp4 = new Employee(4);
        emp4.setDepartment(Department.SALES);

        testee = new Company(Arrays.asList(emp1, emp2, emp3, emp4));
    }

    @Test
    public void sizeEmployees_全従業員数を取得できること() throws Exception {
        assertThat(testee.sizeEmployees()).isEqualTo(4);
    }

    @Test
    public void getEmployeeDevelopmentCount_開発部の従業員数を取得できること() throws Exception {
        assertThat(testee.getEmployeeDevelopmentCount()).isEqualTo(1);
    }

    @Test
    public void getEmployeeFinanceCount_経理部の従業員数を取得できること() throws Exception {
        assertThat(testee.getEmployeeFinanceCount()).isEqualTo(1);
    }

    @Test
    public void getEmployeePersonnelCount_人事部の従業員数を取得できること() throws Exception {
        assertThat(testee.getEmployeePersonnelCount()).isEqualTo(1);
    }

    @Test
    public void getEmployeeSalesCount_営業部の従業員数を取得できること() throws Exception {
        assertThat(testee.getEmployeeSalesCount()).isEqualTo(1);
    }
}
