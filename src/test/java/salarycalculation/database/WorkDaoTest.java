package salarycalculation.database;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKeys;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import salarycalculation.entity.WorkRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link WorkDao}に対するテストクラス。
 *
 * @author naotake
 */
public class WorkDaoTest {

    private WorkDao testee;

    @Rule
    public ExpectedException expect = ExpectedException.none();

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new WorkDao();
    }

    @Test
    public void 該当社員の稼動情報を取得できること() {
        WorkRecord actual = testee.getByYearMonth(1, 201504);

        assertThat(actual.getEmployeeNo(), is(1));
        assertThat(actual.getWorkYearMonth(), is(201504));
        assertThat(actual.getWorkOverTime(), is(BigDecimal.valueOf(10.0)));
    }

    @Test
    public void 存在しない稼動情報を指定した場合に例外が送出されること() {
        expect.expect(RecordNotFoundException.class);
        expect.expect(isClass(WorkRecord.class));
        expect.expect(isKeys(99, 209912));

        testee.getByYearMonth(99, 209912);
    }
}
