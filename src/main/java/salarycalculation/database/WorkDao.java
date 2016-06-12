package salarycalculation.database;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import salarycalculation.database.model.WorkRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * 稼動情報 Dao。
 *
 * @author naotake
 */
public class WorkDao extends BaseDao<WorkRecord> {

    public WorkDao() {
        super();
    }

    /**
     * 該当社員の稼動年月の稼動情報を取得する。
     *
     * @param employeeNo 社員番号
     * @param workYearMonth 稼動年月 (e.g. 201504)
     * @return 稼動情報
     */
    public WorkRecord getByYearMonth(int employeeNo, int workYearMonth) {
        String query = "select * from work where employeeNo = ? and workYearMonth = ?";

        WorkRecord result = getByQuery(query, employeeNo, workYearMonth);
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
    public List<WorkRecord> findAll(int employeeNo) {
        String query = "select * from work where employeeNo = ?";
        return findByQuery(query, employeeNo);
    }

    @Override
    protected BeanHandler<WorkRecord> newBeanHandler() {
        return new BeanHandler<WorkRecord>(WorkRecord.class);
    }

    @Override
    protected BeanListHandler<WorkRecord> newBeanListHandler() {
        return new BeanListHandler<WorkRecord>(WorkRecord.class);
    }
}
