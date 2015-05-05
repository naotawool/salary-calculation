package salarycalculation.database;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
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
        List<Employee> actuals = testee.findAll();

        assertThat(actuals, hasSize(4));
        assertThat(actuals.get(0).getNo(), is(1));
        assertThat(actuals.get(1).getNo(), is(2));
        assertThat(actuals.get(2).getNo(), is(3));
        assertThat(actuals.get(3).getNo(), is(4));
    }
}
