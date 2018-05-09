package refactoring;

import java.time.LocalDate;

public class User {

    /** ID */
    private long id;

    /** 氏名 */
    private String name;

    /** 生年月日 */
    private LocalDate birthday;

    /** 性別 */
    private Gender gender;

    public User(long id, String name, LocalDate birthday, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public static enum Gender {
        MAN, WOMAN
    }
}
