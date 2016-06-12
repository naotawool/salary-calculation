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

import salarycalculation.exception.RuntimeSQLException;

/**
 * DbUtils を使ったデータベースアクセスの基底クラス。
 *
 * @author naotake
 * @param <T> エンティティ型
 */
abstract class BaseDao<T> {

    protected Connection connection;

    public BaseDao() {
        String url = "jdbc:h2:./data/salary_calculation";
        try {
            this.connection = DriverManager.getConnection(url, "sa", "");
        } catch (SQLException e) {
            throw new RuntimeSQLException("Connection Failure", e);
        }
    }

    /**
     * クエリを基にエンティティを 1 件取得する。
     *
     * @param query 検索クエリ
     * @param params パラメータ
     * @return 結果
     */
    protected T getByQuery(String query, Object... params) {
        ResultSetHandler<T> rsHandler = newBeanHandler();
        QueryRunner runner = new QueryRunner();

        T result = null;
        try {
            result = runner.query(connection, query, rsHandler, params);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }
        return result;
    }

    /**
     * クエリを基にエンティティの一覧を取得する。
     *
     * @param query 検索クエリ
     * @param params パラメータ
     * @return 結果一覧
     */
    protected List<T> findByQuery(String query, Object... params) {
        ResultSetHandler<List<T>> rsHandler = newBeanListHandler();
        QueryRunner runner = new QueryRunner();

        List<T> results = null;
        try {
            results = runner.query(connection, query, rsHandler, params);
        } catch (SQLException e) {
            throw new RuntimeSQLException("Select Failure", e);
        }
        return results;
    }

    /**
     * クエリを基に対象レコードの件数を取得する。
     *
     * @param query 検索クエリ
     * @param params パラメータ
     * @return 件数
     */
    protected long countByQuery(String query, Object... params) {
        ScalarHandler<Long> scalarHandler = new ScalarHandler<Long>(1);
        QueryRunner runner = new QueryRunner();

        Long result = null;
        try {
            result = runner.query(connection, query, scalarHandler, params).longValue();
        } catch (SQLException e) {
            throw new RuntimeSQLException("Count Failure", e);
        }
        return result;
    }

    protected abstract BeanHandler<T> newBeanHandler();

    protected abstract BeanListHandler<T> newBeanListHandler();

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
