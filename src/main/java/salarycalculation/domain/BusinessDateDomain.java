package salarycalculation.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import salarycalculation.utils.LocalDates;

/**
 * 業務日付を扱うドメイン。
 *
 * @author naotake
 */
public class BusinessDateDomain {

    private final LocalDate calendar;

    /**
     * コンストラクタ.
     */
    private BusinessDateDomain(LocalDate date) {
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

    public int calculatePeriodByMonth(BusinessDateDomain anotherDate) {
        Objects.requireNonNull(anotherDate);
        Period period = anotherDate.getAsLocalDate().until(this.getAsLocalDate());
        return Math.abs(period.getMonths() + (period.getYears() * 12));
    }

    /**
     * ファクトリメソッド.
     *
     * @return 生成した時点の業務日付
     */
    public static BusinessDateDomain now() {
        return of(LocalDate.now());
    }

    public static BusinessDateDomain of(Date date) {
        return of(LocalDates.toLocalDate(date));
    }

    public static BusinessDateDomain of(Calendar now) {
        return of(new Date(now.getTimeInMillis()));
    }

    public static BusinessDateDomain of(LocalDate date) {
        return new BusinessDateDomain(date);
    }

}
