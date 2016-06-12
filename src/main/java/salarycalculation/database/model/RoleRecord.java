package salarycalculation.database.model;

import lombok.Data;

/**
 * 役割等級を表す Entity。
 *
 * @author naotake
 */
@Data
public class RoleRecord {

    /** 等級 */
    private String rank;

    /** 金額 */
    private int amount;
}
