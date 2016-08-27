package salarycalculation.utils;

/**
 * 従業員の名前を表す ValueObject。
 *
 * @author MASAYUKI
 */
public class PersonName extends BaseValueObject {

    private final String fullName;

    public PersonName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
