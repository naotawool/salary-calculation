package salarycalculation.utils;

public class PersonName extends BaseValueObject {

    private final String fullName;

    public PersonName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

}
