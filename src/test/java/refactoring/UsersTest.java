package refactoring;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import refactoring.User.Gender;

/**
 * {@link Users}に対するテストクラス。
 */
public class UsersTest {

    private Users testee;

    @Before
    public void before() throws Exception {

        User user1 = new User(1, "John", LocalDate.now().minusYears(32), Gender.MAN);
        User user2 = new User(2, "Kate", LocalDate.now().minusYears(19), Gender.WOMAN);
        User user3 = new User(3, "Sam", LocalDate.now().minusYears(20), Gender.MAN);
        User user4 = new User(4, "Penny", LocalDate.now().minusYears(24), Gender.WOMAN);
        User user5 = new User(5, "Smith", LocalDate.now().minusYears(34), Gender.MAN);
        User user6 = new User(6, "Ben", LocalDate.now().minusYears(29), Gender.MAN);
        testee = new Users(Arrays.asList(user1, user2, user3, user4, user5, user6));
    }

    @Test
    public void ユーザ一覧から男性の件数を取得できること() throws Exception {
        long actual = testee.getManCount();
        assertThat(actual).isEqualTo(4);
    }

    @Test
    public void ユーザ一覧から20代の件数を取得できること() throws Exception {
        long actual = testee.getTwentiesCount();
        assertThat(actual).isEqualTo(3);
    }

    @Test
    public void ユーザ一覧から30代の件数を取得できること() throws Exception {
        long actual = testee.getThirtiesCount();
        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void ユーザ一覧から10代女性の件数を取得できること() throws Exception {
        long actual = testee.getTeensWomanCount();
        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void nullユーザの追加判定がfalseであること() throws Exception {
        assertThat(testee.canAdd(null)).isFalse();
    }

    @Test
    public void ユーザIDが0以下のユーザ追加判定がfalseであること() throws Exception {
        User target = new User(0, "hoge", LocalDate.of(1987, 7, 18), Gender.MAN);
        assertThat(testee.canAdd(target)).isFalse();
    }

    @Test
    public void 年齢が10歳未満のユーザ追加判定がfalseであること() throws Exception {
        User target = new User(101, "hoge", LocalDate.of(2017, 7, 18), Gender.MAN);
        assertThat(testee.canAdd(target)).isFalse();
    }

    @Test
    public void 性別がnullのユーザ追加判定がfalseであること() throws Exception {
        User target = new User(102, "hoge", LocalDate.of(1987, 7, 18), null);
        assertThat(testee.canAdd(target)).isFalse();
    }

    @Test
    public void 存在するユーザIDを持つユーザ追加判定がfalseであること() throws Exception {
        User target = new User(1, "hoge", LocalDate.of(1987, 11, 22), Gender.MAN);
        assertThat(testee.canAdd(target)).isFalse();
    }

    @Test
    public void 正常ユーザのユーザ追加判定がtrueであること() throws Exception {
        User target = new User(101, "hoge", LocalDate.of(1987, 11, 22), Gender.MAN);
        assertThat(testee.canAdd(target)).isTrue();
    }
}
