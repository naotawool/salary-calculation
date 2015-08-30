package salarycalculation.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * 業務日付を扱うドメイン。
 *
 * @author naotake
 */
public class BusinessDateDomain {

    private Calendar calendar;

    public BusinessDateDomain() {
        this.calendar = Calendar.getInstance();
    }

    public Calendar getNowAsCalendar() {
        return calendar;
    }

    public Date getNowAsDate() {
        return getNowAsCalendar().getTime();
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
