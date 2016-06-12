package salarycalculation.database;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import salarycalculation.database.model.OrganizationRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * 組織 Dao。
 *
 * @author naotake
 */
public class OrganizationDao extends BaseDao<OrganizationRecord> {

    public OrganizationDao() {
        super();
    }

    /**
     * 組織コードを基に組織情報を取得する。
     *
     * @param code 組織コード
     * @return 組織情報
     */
    public OrganizationRecord get(String code) {
        String query = "select * from organization where code = ?";

        OrganizationRecord result = getByQuery(query, code);
        if (result == null) {
            throw new RecordNotFoundException(OrganizationRecord.class, code);
        }
        return result;
    }

    @Override
    protected BeanHandler<OrganizationRecord> newBeanHandler() {
        return new BeanHandler<OrganizationRecord>(OrganizationRecord.class);
    }

    @Override
    protected BeanListHandler<OrganizationRecord> newBeanListHandler() {
        return new BeanListHandler<OrganizationRecord>(OrganizationRecord.class);
    }
}
