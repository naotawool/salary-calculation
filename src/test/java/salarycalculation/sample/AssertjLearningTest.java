package salarycalculation.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
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

		// AssertJ style
		assertThat(actual.age).isEqualTo(28);
		assertThat(actual.name).isNotNull().isEqualTo("愛媛 太郎");
	}

	@Test
	public void インスタンス検証() {
		Learning actual1 = fixture();
		Learning actual2 = fixture();

		assertThat(actual1, sameInstance(actual1));
		assertThat(actual1, is(not(actual2)));
		assertThat(actual1, is(comparesEqualTo(actual2)));

		// AssertJ style
		assertThat(actual1).isSameAs(actual1).isNotSameAs(actual2).isNotEqualTo(actual2).isEqualByComparingTo(actual2);
	}

	@Test
	public void 文字列比較() {
		// 部分比較
		String actual = "愛媛 蜜柑";
		assertThat(actual, startsWith("愛媛"));
		assertThat(actual, endsWith("蜜柑"));

		// AssertJ style
		assertThat(actual).startsWith("愛媛").endsWith("蜜柑");

		// 空文字比較
		actual = "";
		assertThat(actual, isEmptyString());

		// AssertJ style
		assertThat(actual).isEmpty();

		// toString 比較
		Learning fixture = fixture();
		assertThat(fixture, hasToString("Person(28)[愛媛 太郎]"));

		// AssertJ style
		assertThat(fixture).hasToString("Person(28)[愛媛 太郎]");
	}

	@Test
	public void 数値比較() {
		Learning actual = fixture();

		assertThat(actual.age.doubleValue(), is(closeTo(20.0, 29.0)));
		assertThat(actual.age, greaterThanOrEqualTo(20));
		assertThat(actual.age, lessThan(30));

		// AssertJ style
		assertThat(actual.age).isBetween(20, 29).isGreaterThanOrEqualTo(20).isLessThan(30);
	}

	@Test
	public void 日付比較() {
		Learning actual = fixture();

		String actualAsStr = FastDateFormat.getInstance("yyyy-MM-dd").format(actual.birthday);
		assertThat(actualAsStr, is("1987-07-18"));

		// AssertJ style
		assertThat(actual.birthday).hasYear(1987).hasMonth(7).hasDayOfMonth(18).hasSameTimeAs("1987-07-18");
	}

	@Test
	public void Collection比較() {
		List<String> actuals = Lists.newArrayList("愛媛県", "大阪府", "沖縄県");

		assertThat(actuals, is(allOf(hasSize(3), not(empty()))));
		assertThat(actuals, hasItems("大阪府", "沖縄県"));
		assertThat(actuals, not(hasItem("東京都")));

		// AssertJ style
		assertThat(actuals).hasSize(3).isNotEmpty().contains("大阪府", "沖縄県").doesNotContain("東京都");
	}

	private static Learning fixture() {

		Learning fixture = new Learning();
		fixture.age = 28;
		fixture.name = "愛媛 太郎";
		fixture.birthday = DateUtil.parse("1987-07-18");

		return fixture;
	}

	private static class Learning implements Comparable<Learning> {

		Integer age;
		String name;
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
