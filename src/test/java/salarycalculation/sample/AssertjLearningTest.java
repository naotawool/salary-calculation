package salarycalculation.sample;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;
import org.assertj.core.util.DateUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * AssertJ 学習用のテストクラス。
 *
 * @author naotake
 */
public class AssertjLearningTest {

    @Test
    public void AssertJの基本的な使い方() {

        Learning actual = fixture();

        // 同値比較
        assertThat(actual.age, is(28));
        assertThat(actual.name, is(notNullValue()));
        assertThat(actual.name, is("愛媛 太郎"));
    }

    @Test
    public void インスタンス検証() {
        Learning actual1 = fixture();
        Learning actual2 = fixture();

        assertThat(actual1, sameInstance(actual1));
        assertThat(actual1, is(not(actual2)));
        assertThat(actual1, is(comparesEqualTo(actual2)));
    }

    @Test
    public void 文字列比較() {
        // 部分比較
        String actual = "愛媛 蜜柑";
        assertThat(actual, startsWith("愛媛"));
        assertThat(actual, endsWith("蜜柑"));

        // 空文字比較
        actual = "";
        assertThat(actual, isEmptyString());

        // toString 比較
        Learning fixture = fixture();
        assertThat(fixture, hasToString("Person(28)[愛媛 太郎]"));
    }

    @Test
    public void 数値比較() {
        Learning actual = fixture();

        assertThat(actual.age.doubleValue(), is(closeTo(20.0, 29.0)));
        assertThat(actual.age, greaterThanOrEqualTo(20));
        assertThat(actual.age, lessThan(30));
    }

    @Test
    public void 日付比較() {
        Learning actual = fixture();

        String actualAsStr = FastDateFormat.getInstance("yyyy-MM-dd").format(actual.birthday);
        assertThat(actualAsStr, is("1987-07-18"));
    }

    @Test
    public void Collection比較() {
        List<String> actuals = Lists.newArrayList("愛媛県", "大阪府", "沖縄県");

        assertThat(actuals, is(allOf(hasSize(3), not(empty()))));
        assertThat(actuals, hasItems("大阪府", "沖縄県"));
        assertThat(actuals, not(hasItem("東京都")));
    }

    private static Learning fixture() {

        Learning fixture = new Learning();
        fixture.age = 28;
        fixture.name = "愛媛 太郎";
        fixture.birthday = DateUtil.parse("1987-07-18");

        return fixture;
    }

    private static class Learning implements Comparable<Learning> {

        /** 年齢 */
        Integer age;
        /** 名前 */
        String name;
        /** 誕生日 */
        Date birthday;

        @Override
        public int compareTo(Learning other) {
            return age.compareTo(other.age);
        }

        @Override
        public String toString() {
            return String.format("Person(%02d)[%s]", age, name);
        }
    }
}
