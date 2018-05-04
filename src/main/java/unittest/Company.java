package unittest;

import java.time.LocalDate;
import java.util.List;

import unittest.Employee.Department;

/**
 * 会社を表すクラス。
 *
 * @author naotake
 */
public class Company {

    private List<Employee> employees;

    public Company(List<Employee> employees) {
        this.employees = employees;
    }

    public int sizeEmployees() {
        return employees.size();
    }

    /**
     * 開発部の従業員数を取得します。
     *
     * @return 従業員数
     */
    public int getEmployeeDevelopmentCount() {
        int development = 0;
        if (employees.size() != 0) {
            for (Employee employee : employees) {
                if (employee.getDepartment() == Department.DEVELOPMENT) {
                    development = development + 1;
                }
            }
        }
        return development;
    }

    /**
     * 経理部の従業員数を取得します。
     *
     * @return 従業員数
     */
    public int getEmployeeFinanceCount() {
        int development = 0;
        if (employees.size() != 0) {
            for (Employee employee : employees) {
                if (employee.getDepartment() == Department.FINANCE) {
                    development = development + 1;
                }
            }
        }
        return development;
    }

    /**
     * 人事部の従業員数を取得します。
     *
     * @return 従業員数
     */
    public int getEmployeePersonnelCount() {
        int development = 0;
        if (employees.size() != 0) {
            for (Employee employee : employees) {
                if (employee.getDepartment() == Department.SALES) {
                    development = development + 1;
                }
            }
        }
        return development;
    }

    /**
     * 営業部の従業員数を取得します。
     *
     * @return 従業員数
     */
    public int getEmployeeSalesCount() {
        int development = 0;
        if (employees.size() != 0) {
            for (Employee employee : employees) {
                if (employee.getDepartment() == Department.PERSONNEL) {
                    development = development + 1;
                }
            }
        }
        return development;
    }

    public int getEmployeeTwenties() {
        // カウンター初期化
        int x = 0;
        // 社員が空かどうかを判定
        if (employees.size() != 0) {
            for (Employee employee : employees) {
                // 社員の年齢を取得
                int age = employee.calcAge(LocalDate.now());
                // 20 歳代かどうかを判定
                if (age >= 20 && age <= 30)
                    System.out.println(employee.getName() + ":" + age);
                    x = x + 1;
            }
        }
        return x;
    }
}
