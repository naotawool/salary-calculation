package salarycalculation.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
    private LocalDate calendar;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {

        calendar = LocalDate.of(2014, 3, 1);
        testee = BusinessDateDomain.of(calendar);
    }

    @Test
    public void 内部で保持するCalendarインスタンスを取得できること() {
        assertThat(testee.getAsLocalDate(), sameInstance(calendar));
    }

    @Test
    public void 内部で保持するCalendarインスタンスを基にDateインスタンスを取得できること() {
        // 実行
        Date actual = testee.getAsDate();

        // 検証
        assertThat(actual.getTime(), is(calendar.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
    }

}
