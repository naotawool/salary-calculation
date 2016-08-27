package salarycalculation.web.representation;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import salarycalculation.utils.PersonName;

/**
 * 従業員の情報を表示するためのビュークラス。
 *
 * @author naotake
 */
public class EmployeeView {

    private final Integer no;

    @JsonProperty("person_name")
    private final String name;

    public EmployeeView(Integer no, PersonName name) {
        this.no = no;
        this.name = name.getFullName();
    }

    public Integer getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
