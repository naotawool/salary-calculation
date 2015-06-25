package salarycalculation.database;

import static org.junit.Assert.assertThat;
import static salarycalculation.matchers.OrderEmployee.orderNos;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import salarycalculation.entity.Employee;

/**
 * {@link EmployeeDao}に対するテストクラス。
 *
 * @author naotake
 */
public class EmployeeDaoTest {

    private EmployeeDao testee;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new EmployeeDao();
    }

    @Test
    public void 社員一覧を社員番号の昇順で取得できること() {
        List<Employee> actuals = testee.findAll(true);

        assertThat(actuals, orderNos(1, 2, 3, 4));
    }

    @Test
    public void 社員一覧を社員番号の降順で取得できること() {
        List<Employee> actuals = testee.findAll(false);

        assertThat(actuals, orderNos(4, 3, 2, 1));
    }

    @Ignore
    @Test
    public void OrderEmployee_orderNosが失敗するテスト() {
        List<Employee> actuals = testee.findAll(false);

        assertThat(actuals, orderNos(4, 3, 2));
    }
}
