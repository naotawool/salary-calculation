package salarycalculation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import salarycalculation.entity.WorkRecord;
import salarycalculation.exception.RecordNotFoundException;
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
    public WorkRecord getByYearMonth(int employeeNo, int workYearMonth) {
        ResultSetHandler<WorkRecord> rsHandler = new BeanHandler<WorkRecord>(WorkRecord.class);
        QueryRunner runner = new QueryRunner();

        WorkRecord result = null;
        try {
            result = runner.query(connection, "select * from work where employeeNo = " + employeeNo
                    + " and workYearMonth = " + workYearMonth, rsHandler);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }

        if (result == null) {
            throw new RecordNotFoundException(WorkRecord.class, employeeNo, workYearMonth);
        }

        return result;
    }

    /**
     * 該当社員の全稼動年月の稼動情報を取得する。
     *
     * @param employeeNo 社員番号
     * @param workYearMonth 稼動年月 (e.g. 201504)
     * @return 稼動情報
     */
    // @UT
    public List<WorkRecord> findAll(int employeeNo) {
        ResultSetHandler<List<WorkRecord>> rsHandler = new BeanListHandler<WorkRecord>(WorkRecord.class);
        QueryRunner runner = new QueryRunner();

        List<WorkRecord> result = null;
        try {
            result = runner.query(connection, "select * from work where employeeNo = " + employeeNo, rsHandler);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }

        if (result == null) {
            throw new RecordNotFoundException(WorkRecord.class, employeeNo);
        }

        return result;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
