package salarycalculation.database;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import salarycalculation.database.model.CapabilityRecord;
import salarycalculation.domain.employee.Capability;
import salarycalculation.exception.RecordNotFoundException;

/**
 * 能力等級 Dao。
 *
 * @author naotake
 */
public class CapabilityDao extends BaseDao<CapabilityRecord> {

    public CapabilityDao() {
        super();
    }

    /**
     * 等級を基に能力等級を取得する。
     *
     * @param rank 等級
     * @return 能力等力
     */
    public CapabilityRecord get(String rank) {
        String query = "select * from capability where rank = ?";

        CapabilityRecord result = getByQuery(query, rank);
        if (result == null) {
            throw new RecordNotFoundException(Capability.class, rank);
        }
        return result;
    }

    @Override
    protected BeanHandler<CapabilityRecord> newBeanHandler() {
        return new BeanHandler<CapabilityRecord>(CapabilityRecord.class);
    }

    @Override
    protected BeanListHandler<CapabilityRecord> newBeanListHandler() {
        return new BeanListHandler<CapabilityRecord>(CapabilityRecord.class);
    }
}
