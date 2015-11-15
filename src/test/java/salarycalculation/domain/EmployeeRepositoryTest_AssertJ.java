package salarycalculation.domain;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

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
        testee = new EmployeeRepository();
    }

    /**
     * Hands-on: 2-1
     */
    @Test
    public void 社員一覧を社員番号の昇順に取得できること() {
        // 実行
        List<EmployeeDomain> actuals = testee.findAll();

        // TODO 期待する社員番号の順番は「1, 2, 3, 4」
        fail("未検証");
    }

    /**
     * Hands-on: 2-2
     */
    @Test
    public void 社員一覧を想定年収の昇順に取得できること() {
        // 実行
        List<EmployeeDomain> actuals = testee.findAllOrderByAnnualSalary(true);

        // TODO 期待する社員名の順番は「"東京 次郎", "愛媛 蜜柑", "大阪 太郎", "埼玉 花子"」
        fail("未検証");
    }

    /**
     * Hands-on: 3
     */
    @Test
    public void 社員番号を基に情報を取得できること() {
        // 社員番号[1]の場合
        EmployeeDomain actual = testee.get(fixture.no);

        assertThat(actual.getNo(), is(fixture.expectedNo));
        assertThat(actual.getEntity().getName(), is(fixture.expectedName));
        assertThat(actual.getRole().getRank(), is(fixture.expectedRoleRank));
        assertThat(actual.getCapability().getRank(), is(fixture.expectedCapabilityRank));
        assertThat(actual.getOrganization().getName(), is(fixture.expectedOrganizationName));

        // TODO assertThat(actual).hasNo(fixture.expectedNo);
        // TODO assertThat(actual).hasName(fixture.expectedName);
        // TODO assertThat(actual).hasRoleRank(fixture.expectedRoleRank);
        // TODO assertThat(actual).hasCapabilityRank(fixture.expectedCapabilityRank);
        // TODO assertThat(actual).hasOrganizationRank(fixture.expectedOrganizationName);
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
