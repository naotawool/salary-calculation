package salarycalculation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import salarycalculation.entity.Employee;
import salarycalculation.exception.RecordNotFoundException;
import salarycalculation.exception.RuntimeSQLException;

/**
 * 社員情報 Dao。
 *
 * @author naotake
 */
public class EmployeeDao {

    private Connection connection;

    public EmployeeDao() {
        String url = "jdbc:h2:./data/salary_calculation";
        try {
            this.connection = DriverManager.getConnection(url, "sa", "");
        } catch (SQLException e) {
            throw new RuntimeSQLException("Connection Failure", e);
        }
    }

    /**
     * 社員番号を基に社員情報を取得する。
     *
     * @param no 社員番号
     * @return 社員情報
     */
    // @UT
    public Employee get(String no) {
        ResultSetHandler<Employee> rsHandler = new BeanHandler<Employee>(Employee.class);
        QueryRunner runner = new QueryRunner();

        Employee result = null;
        try {
            result = runner.query(connection, "select * from employee where no = " + no, rsHandler);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }

        if (result == null) {
            throw new RecordNotFoundException(Employee.class, no);
        }

        return result;
    }

    /**
     * 社員情報の一覧を取得する。<br />
     * 一覧は社員番号の指定したソート順に並び替えられている。
     *
     * @param ascending 社員番号の昇順かどうか
     * @return 社員情報一覧
     */
    // @UT
    public List<Employee> findAll(boolean ascending) {
        ResultSetHandler<List<Employee>> rsHandler = new BeanListHandler<Employee>(Employee.class);
        QueryRunner runner = new QueryRunner();

        String ordering = (ascending ? "asc" : "desc");

        List<Employee> results = null;
        try {
            results = runner.query(connection, "select * from employee order by no " + ordering,
                    rsHandler);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }
        return results;
    }

    /**
     * 指定した役割等級の社員情報一覧を取得する。
     *
     * @param rank 取得対象の役割等級
     * @return 社員情報一覧
     */
    // @UT
    public List<Employee> findByRole(String rank) {
        ResultSetHandler<List<Employee>> rsHandler = new BeanListHandler<Employee>(Employee.class);
        QueryRunner runner = new QueryRunner();

        List<Employee> results = null;
        try {
            results = runner.query(connection, "select * from employee where roleRank = '" + rank
                    + "' order by no", rsHandler);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }
        return results;
    }

    /**
     * 指定した能力等級の社員情報一覧を取得する。
     *
     * @param rank 取得対象の能力等級
     * @return 社員情報一覧
     */
    // @UT
    public List<Employee> findByCapability(String rank) {
        ResultSetHandler<List<Employee>> rsHandler = new BeanListHandler<Employee>(Employee.class);
        QueryRunner runner = new QueryRunner();

        List<Employee> results = null;
        try {
            results = runner.query(connection, "select * from employee where capabilityRank = '"
                    + rank + "' order by no", rsHandler);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }
        return results;
    }

    /**
     * 指定された組織コードに該当する社員数を取得する。
     *
     * @param organizationCode 組織コード
     * @return 社員数
     */
    public long countByOrganization(String organizationCode) {
        ScalarHandler<Long> scalarHandler = new ScalarHandler<Long>(1);
        QueryRunner runner = new QueryRunner();

        Long result = null;
        try {
            result = runner.query(connection,
                    "select count(*) from employee where organization = ?", scalarHandler,
                    organizationCode).longValue();
        } catch (SQLException e) {
            throw new RuntimeSQLException("Count Failure", e);
        }

        return result;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
