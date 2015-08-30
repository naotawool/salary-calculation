package salarycalculation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import salarycalculation.entity.Organization;
import salarycalculation.exception.RecordNotFoundException;
import salarycalculation.exception.RuntimeSQLException;

/**
 * 組織 Dao。
 *
 * @author naotake
 */
public class OrganizationDao {

    private Connection connection;

    public OrganizationDao() {
        String url = "jdbc:h2:./data/salary_calculation";
        try {
            this.connection = DriverManager.getConnection(url, "sa", "");
        } catch (SQLException e) {
            throw new RuntimeSQLException("Connection Failure", e);
        }
    }

    /**
     * 組織コードを基に組織情報を取得する。
     *
     * @param code 組織コード
     * @return 組織情報
     */
    public Organization get(String code) {
        ResultSetHandler<Organization> rsHandler = new BeanHandler<Organization>(Organization.class);
        QueryRunner runner = new QueryRunner();

        Organization result = null;
        try {
            result = runner.query(connection, "select * from organization where code = ?",
                    rsHandler, code);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }

        if (result == null) {
            throw new RecordNotFoundException(Organization.class, code);
        }

        return result;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
