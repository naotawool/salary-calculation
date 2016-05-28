package salarycalculation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static salarycalculation.assertions.MyProjectAssertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

import salarycalculation.assertions.MyProjectSoftAssertions;
import salarycalculation.database.repository.EmployeeRepositoryDao;
import salarycalculation.entity.EmployeeRecord;
import salarycalculation.utils.PersonName;

/**
 * {@link EmployeeRepository}に対する AssertJ を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeRepositoryTest_AssertJ {

    private EmployeeRepository testee;

    @DataPoint
    public static Fixture fixture = new Fixture("1", 1, "愛媛 蜜柑", "A3", "SE", "開発部2グループ");

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new EmployeeRepositoryDao();
    }

    /**
     * Hands-on: 2-1
     */
    @Test
    public void 社員一覧を社員番号の昇順に取得できること() {
        // 実行
        List<Employee> actuals = testee.findAll().getEmployees();

        // 社員番号を取り出す
        List<Integer> actualNos = actuals.stream().map(Employee::getId).collect(Collectors.toList());

        // JUnit4 での検証
        assertThat(actualNos.size(), is(4));
        assertThat(actualNos, contains(1, 2, 3, 4));

        // AssertJ style
        assertThat(actuals).extracting(Employee::getId)
                .hasSize(4)
                .containsSequence(1, 2, 3, 4);
    }

    /**
     * Hands-on: 2-2
     */
    @Test
    public void 社員一覧を想定年収の昇順に社員名を取得できること() {
        // 実行
        List<Employee> actuals = testee.findAllOrderByAnnualSalary(true).getEmployees();

        // 社員番号を取り出す
        List<String> actualNames = actuals.stream().map(Employee::getName).map(PersonName::getFullName)
                .collect(Collectors.toList());

        // JUnit4 での検証
        assertThat(actualNames.size(), is(4));
        assertThat(actualNames, contains("東京 次郎", "愛媛 蜜柑", "大阪 太郎", "埼玉 花子"));

        // AssertJ style
        assertThat(actuals).extracting(Employee::getName).extracting(PersonName::getFullName)
                .hasSize(4)
                .containsSequence("東京 次郎", "愛媛 蜜柑", "大阪 太郎", "埼玉 花子");
    }

    /**
     * Hands-on: 3
     */
    @Test
    public void 社員番号を基に情報を取得できること() {
        // 社員番号[1]の場合
        Employee actual = testee.get(fixture.no);

        assertThat(actual.getId(), is(fixture.expectedNo));
        assertThat(actual.getName().getFullName(), is(fixture.expectedName));
        assertThat(actual.getRole().getRank(), is(fixture.expectedRoleRank));
        assertThat(actual.getCapability().getRank().name(), is(fixture.expectedCapabilityRank));
        assertThat(actual.getOrganization().getName(), is(fixture.expectedOrganizationName));

        // AssertJ style
        assertThat(actual).hasNo(fixture.expectedNo).hasName(fixture.expectedName)
                .hasRoleRank(fixture.expectedRoleRank)
                .hasCapabilityRank(fixture.expectedCapabilityRank)
                .hasOrganizationRank(fixture.expectedOrganizationName);
    }

    /**
     * Hands-on: 4-2
     */
    @Test
    public void SoftAssertionsを使って社員番号を基に情報を取得できること() {
        // 社員番号[1]の場合
        Employee actual = testee.get(fixture.no);

        // AssertJ style
        MyProjectSoftAssertions softly = new MyProjectSoftAssertions();

        softly.assertThat(actual).hasNo(fixture.expectedNo).hasName(fixture.expectedName)
                .hasRoleRank(fixture.expectedRoleRank)
                .hasCapabilityRank(fixture.expectedCapabilityRank)
                .hasOrganizationRank(fixture.expectedOrganizationName);

        softly.assertAll();
    }

    static class Fixture {

        /** 取得対象の社員番号 */
        String no;
        /** 期待する社員番号 */
        int expectedNo;
        /** 期待する社員名 */
        String expectedName;
        /** 期待する役割等級 */
        String expectedRoleRank;
        /** 期待する能力等級 */
        String expectedCapabilityRank;
        /** 期待する組織名 */
        String expectedOrganizationName;

        public Fixture(String no, int expectedNo, String expectedName, String expectedRoleRank,
                String expectedCapabilityRank, String expectedOrganizationName) {
            this.no = no;
            this.expectedNo = expectedNo;
            this.expectedName = expectedName;
            this.expectedRoleRank = expectedRoleRank;
            this.expectedCapabilityRank = expectedCapabilityRank;
            this.expectedOrganizationName = expectedOrganizationName;
        }
    }
}
