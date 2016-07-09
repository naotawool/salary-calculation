package salarycalculation.web.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;
import salarycalculation.utils.PersonName;

/**
 * 従業員の情報を表示するためのビュークラス。
 *
 * @author naotake
 */
@Getter
@ToString
public class EmployeeView {

    private final Integer no;

    @JsonProperty("person_name")
    private final String name;

    public EmployeeView(Integer no, PersonName name) {
        this.no = no;
        this.name = name.getFullName();
    }
}
