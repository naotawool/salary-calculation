package salarycalculation.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static salarycalculation.matchers.OrderEmployeeDomain.orderNos;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link EmployeeRepository}に対するテストクラス。
 *
 * @author naotake
 */
@RunWith(Theories.class)
public class EmployeeRepositoryTest {

    private EmployeeRepository testee;

    @DataPoint
    public static Fixture fixture1 = new Fixture("1", 1, "愛媛 蜜柑", "A3", "SE", "開発部2グループ");
    @DataPoint
    public static Fixture fixture2 = new Fixture("2", 2, "大阪 太郎", "C4", "PL", "開発部1グループ");
    @DataPoint
    public static Fixture fixture3 = new Fixture("3", 3, "埼玉 花子", "M2", "PM", "開発部1グループ");
    @DataPoint
    public static Fixture fixture4 = new Fixture("4", 4, "東京 次郎", "A1", "AS", "開発部3グループ");

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new EmployeeRepository();
    }

    @Theory
    public void 社員番号を基に情報を取得できること(Fixture fixture) {
        EmployeeDomain actual = testee.get(fixture.no);
        assertThat(actual.getNo(), is(fixture.expectedNo));
        assertThat(actual.getEntity().getName(), is(fixture.expectedName));
        assertThat(actual.getRole().getRank(), is(fixture.expectedRoleRank));
        assertThat(actual.getCapability().getRank(), is(fixture.expectedCapabilityRank));
        assertThat(actual.getOrganization().getName(), is(fixture.expectedOrganizationName));
    }

    @Test
    public void 社員一覧を取得できること() {
        List<EmployeeDomain> actuals = testee.findAll();

        assertThat(actuals.size(), is(4));
        assertThat(actuals, orderNos(1, 2, 3, 4));
    }

    @Test
    public void 社員一覧を想定年収の昇順に取得できること() {
        List<EmployeeDomain> actuals = testee.findAllOrderByAnnualSalary(true);

        assertThat(actuals.size(), is(4));
        assertThat(actuals, orderNos(4, 1, 2, 3));
    }

    @Test
    public void 社員一覧を想定年収の降順に取得できること() {
        List<EmployeeDomain> actuals = testee.findAllOrderByAnnualSalary(false);

        assertThat(actuals.size(), is(4));
        assertThat(actuals, orderNos(3, 2, 1, 4));
    }

    @Test
    public void 指定年月の全社員の給与総支給額を取得できること() {
        assertThat(testee.getSumTotalSalary(201504), is(1965412));
    }

    @Test
    public void 指定年月の全社員の手取り額平均を取得できること() {
        assertThat(testee.getAverageTakeHome(201504), is(458387));
    }

    @Test
    public void 指定した額を超える想定年収の社員数を取得できること() {
        assertThat(testee.getCountByOverAnnualSalary(4000000), is(3));

        assertThat(testee.getCountByOverAnnualSalary(5000000), is(2));
    }

    @Test
    public void 勤続月数の最大最小の社員情報を取得できること() {
        // 最大
        EmployeeDomain actual = testee.getByDurationMonth(true);
        assertThat(actual.getNo(), is(3));

        // 最小
        actual = testee.getByDurationMonth(false);
        assertThat(actual.getNo(), is(4));
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
