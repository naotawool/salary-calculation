package refactoring;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Predicate;

import refactoring.User.Gender;

public class Users {

    private List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    /**
     * 男性ユーザの人数をカウントする。
     *
     * @return 男性ユーザの人数
     */
    public long countMan() {
        return users.stream().filter(User::isMan).count();
    }

    // TODO JavaDoc 書く
    public long getTwentiesCount() {
        return users.stream().filter(getAgeCondition(20, 29)).count();
    }

    private Predicate<User> getAgeCondition(int min, int max) {
        return t -> (20 <= t.getCurrentAge() && t.getCurrentAge() <= 29);
    }

    // TODO JavaDoc 書く
    public long getThirtiesCount() {
        long sum = 0;
        for (User user : users) {
            int year = Period.between(user.getBirthday(), LocalDate.now()).getYears();
            if (year >= 30 && year < 40) {
                sum = sum + 1;
            }
        }
        return sum;
    }

    // TODO JavaDoc 書く
    public long getTeensWomanCount() {
        long sum = 0;
        for (User user : users) {
            if (user.getGender() == Gender.WOMAN) {
                int year = Period.between(user.getBirthday(), LocalDate.now()).getYears();
                if (year >= 10 && year < 20) {
                    sum = sum + 1;
                }
            }
        }
        return sum;
    }

    /**
     * 指定されたユーザを一覧に追加可能かどうかを判定する。
     * 下記条件に 1 つでも該当する場合、追加できない。
     *
     * <ul>
     * <li>追加対象ユーザが null</li>
     * <li>追加対象ユーザの ID が 0 以下</li>
     * <li>追加対象ユーザの年齢が 10 歳未満</li>
     * <li>追加対象ユーザの性別が null</li>
     * <li>追加対象ユーザの ID が既に存在している</li>
     * </ul>
     *
     * @param target 追加対象ユーザ
     * @return 追加可能な場合は true
     */
    public boolean canAdd(User target) {
        boolean result = true;
        if (target == null) {
            result = false;
        } else {
            if (target.getId() <= 0) {
                result = false;
            } else {
                int year = Period.between(target.getBirthday(), LocalDate.now()).getYears();
                if (year < 10) {
                    result = false;
                } else {
                    if (target.getGender() == null) {
                        return false;
                    }
                }
            }
        }

        if (result == false) {
            return false;
        }

        for (User user : users) {
            if (user.getId() == target.getId()) {
                result = false;
            }
        }
        return result;
    }
}
