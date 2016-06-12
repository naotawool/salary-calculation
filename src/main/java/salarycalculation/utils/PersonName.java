package salarycalculation.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 従業員の名前を表す ValueObject。
 *
 * @author MASAYUKI
 */
@AllArgsConstructor
@Getter
@ToString
public class PersonName extends BaseValueObject {

    private final String fullName;
}
