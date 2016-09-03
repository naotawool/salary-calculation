package salarycalculation.domain.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import salarycalculation.utils.BaseValueObject;
import salarycalculation.utils.LocalDates;

/**
 * 業務日付を扱うドメイン。
 *
 * @author naotake
 */
public class BusinessDate extends BaseValueObject {

    private final LocalDate calendar;

    /**
     * コンストラクタ.
     */
    private BusinessDate(LocalDate date) {
        this.calendar = Objects.requireNonNull(date);
    }

    public Calendar getAsCalendar() {
        Calendar now = Calendar.getInstance();
        now.setTime(getAsDate());
        return now;
    }

    public Date getAsDate() {
        return LocalDates.toDate(calendar);
    }

    public LocalDate getAsLocalDate() {
        return calendar;
    }

    public int calculatePeriodByMonth(BusinessDate anotherDate) {
        Objects.requireNonNull(anotherDate);
        Period period = anotherDate.getAsLocalDate().until(this.getAsLocalDate());
        return Math.abs(period.getMonths() + (period.getYears() * 12));
    }

    /**
     * ファクトリメソッド.
     *
     * @return 生成した時点の業務日付
     */
    public static BusinessDate now() {
        return of(LocalDate.now());
    }

    public static BusinessDate of(Date date) {
        return of(LocalDates.toLocalDate(date));
    }

    public static BusinessDate of(Calendar now) {
        return of(now.getTime());
    }

    public static BusinessDate of(LocalDate date) {
        return new BusinessDate(date);
    }

    public static BusinessDate of(int year, int month, int dayOfMonth) {
        return of(LocalDate.of(year, month, dayOfMonth));
    }
}
