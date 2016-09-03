package salarycalculation.database;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;

import salarycalculation.database.model.RoleRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * 役割等級 Dao。
 *
 * @author naotake
 */
public class RoleDao extends BaseDao<RoleRecord> {

    public RoleDao() {
        super();
    }

    /**
     * 等級を基に役割等級を取得する。
     *
     * @param rank 等級
     * @return 役割等級
     */
    public RoleRecord get(String rank) {
        verify(rank);
        String query = "select * from role where rank = ?";

        RoleRecord result = getByQuery(query, rank);
        if (result == null) {
            throw new RecordNotFoundException(RoleRecord.class, rank);
        }
        return result;
    }

    private void verify(String rank) {
        if (StringUtils.isBlank(rank)) {
            throw new NullPointerException("等級は必須です");
        }
        if (StringUtils.length(rank) != 2) {
            String message = String.format("等級は 2 桁で指定してください[%s]", StringUtils.length(rank));
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    protected BeanHandler<RoleRecord> newBeanHandler() {
        return new BeanHandler<RoleRecord>(RoleRecord.class);
    }

    @Override
    protected BeanListHandler<RoleRecord> newBeanListHandler() {
        return new BeanListHandler<RoleRecord>(RoleRecord.class);
    }
}
