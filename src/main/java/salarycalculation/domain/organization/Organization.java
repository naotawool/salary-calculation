package salarycalculation.domain.organization;

import org.apache.commons.lang.StringUtils;

import salarycalculation.utils.BaseEntity;

public class Organization extends BaseEntity<String> {

    /** 組織コード */
    private String code;

    /** 組織名 */
    private String name;

    public Organization(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getId() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean equalsName(String name) {
        return StringUtils.equals(this.name, name);
    }

}
