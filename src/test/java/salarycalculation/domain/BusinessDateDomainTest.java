package salarycalculation.domain;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link BusinessDateDomain}に対するテストクラス。
 *
 * @author naotake
 */
public class BusinessDateDomainTest {

    private BusinessDateDomain testee;
    private Calendar calendar;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new BusinessDateDomain();

        calendar = Calendar.getInstance();
        calendar.set(2014, (4 - 1), 1);

        testee.setCalendar(calendar);
    }

    @Test
    public void 内部で保持するCalendarインスタンスを取得できること() {
        assertThat(testee.getNowAsCalendar(), sameInstance(calendar));
    }

    @Test
    public void 内部で保持するCalendarインスタンスを基にDateインスタンスを取得できること() {
        // 実行
        Date actual = testee.getNowAsDate();

        // 検証
        assertThat(actual.getTime(), is(calendar.getTime().getTime()));
    }
}
