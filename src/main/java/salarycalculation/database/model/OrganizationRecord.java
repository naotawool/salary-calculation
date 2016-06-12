package salarycalculation.database.model;

import lombok.Data;

/**
 * 組織を現す Entity。
 *
 * @author naotake
 */
@Data
public class OrganizationRecord {

    /** 組織コード */
    private String code;

    /** 組織名 */
    private String name;
}
