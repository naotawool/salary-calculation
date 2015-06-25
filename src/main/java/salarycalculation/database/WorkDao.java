package salarycalculation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import salarycalculation.entity.Work;
import salarycalculation.exception.RuntimeSQLException;

/**
 * 稼動情報 Dao。
 *
 * @author naotake
 */
public class WorkDao {

    private Connection connection;

    public WorkDao() {
        String url = "jdbc:h2:./data/salary_calculation";
        try {
            this.connection = DriverManager.getConnection(url, "sa", "");
        } catch (SQLException e) {
            throw new RuntimeSQLException("Connection Failure", e);
        }
    }

    /**
     * 該当社員の稼動年月の稼動情報を取得する。
     *
     * @param employeeNo 社員番号
     * @param workYearMonth 稼動年月 (e.g. 201504)
     * @return 稼動情報
     */
    // @UT
    public Work getByYearMonth(int employeeNo, int workYearMonth) {
        ResultSetHandler<Work> rsHandler = new BeanHandler<Work>(Work.class);
        QueryRunner runner = new QueryRunner();

        Work result = null;
        try {
            result = runner.query(connection, "select * from work where employeeNo = " + employeeNo
                    + " and workYearMonth = " + workYearMonth, rsHandler);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }

        return result;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
