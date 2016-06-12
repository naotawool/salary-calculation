package salarycalculation.domain.organization;

import org.apache.commons.lang.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import salarycalculation.utils.BaseEntity;

@AllArgsConstructor
public class Organization extends BaseEntity<String> {

    /** 組織コード */
    private final String code;

    /** 組織名 */
    @Getter
    private final String name;

    @Override
    public String getId() {
        return code;
    }

    public boolean equalsName(String name) {
        return StringUtils.equals(this.name, name);
    }

}
