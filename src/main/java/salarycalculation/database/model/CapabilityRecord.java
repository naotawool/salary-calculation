package salarycalculation.database.model;

import lombok.Data;

/**
 * 能力等級を表す Entity。
 *
 * @author naotake
 */
@Data
public class CapabilityRecord {

    /** 等級 */
    private String rank;

    /** 金額 */
    private int amount;
}
