package salarycalculation.database;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import salarycalculation.database.model.EmployeeRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * 社員情報 Dao。
 *
 * @author naotake
 */
public class EmployeeDao extends BaseDao<EmployeeRecord> {

    public EmployeeDao() {
        super();
    }

    /**
     * 社員番号を基に社員情報を取得する。
     *
     * @param no 社員番号
     * @return 社員情報
     */
    public EmployeeRecord get(String no) {
        String query = "select * from employee where no = ?";

        EmployeeRecord result = getByQuery(query, no);
        if (result == null) {
            throw new RecordNotFoundException(EmployeeRecord.class, no);
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
    public List<EmployeeRecord> findAll(boolean ascending) {
        String query = "select * from employee order by no " + (ascending ? "asc" : "desc");
        return findByQuery(query);
    }

    /**
     * 指定した役割等級の社員情報一覧を取得する。
     *
     * @param rank 取得対象の役割等級
     * @return 社員情報一覧
     */
    public List<EmployeeRecord> findByRole(String rank) {
        String query = "select * from employee where roleRank = ? order by no";
        return findByQuery(query, rank);
    }

    /**
     * 指定した能力等級の社員情報一覧を取得する。
     *
     * @param rank 取得対象の能力等級
     * @return 社員情報一覧
     */
    public List<EmployeeRecord> findByCapability(String rank) {
        String query = "select * from employee where capabilityRank = ? order by no";
        return findByQuery(query, rank);
    }

    /**
     * 指定された組織コードに該当する社員数を取得する。
     *
     * @param organizationCode 組織コード
     * @return 社員数
     */
    public long countByOrganization(String organizationCode) {
        String query = "select count(*) from employee where organization = ?";
        return countByQuery(query, organizationCode);
    }

    @Override
    protected BeanHandler<EmployeeRecord> newBeanHandler() {
        return new BeanHandler<EmployeeRecord>(EmployeeRecord.class);
    }

    @Override
    protected BeanListHandler<EmployeeRecord> newBeanListHandler() {
        return new BeanListHandler<EmployeeRecord>(EmployeeRecord.class);
    }
}
