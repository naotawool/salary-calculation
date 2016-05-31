package salarycalculation.domain.work;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 勤務時間のID.<br/>
 * サロゲートキーが望ましい.
 *
 * @author MASAYUKI
 *
 */
public class WorkOverTimeId {

    private final Integer employeeNo;
    /** 稼動年月 */
    private final int workYearMonth;

    public WorkOverTimeId(Integer employeeNo, int workYearMonth) {
        super();
        this.employeeNo = employeeNo;
        this.workYearMonth = workYearMonth;
    }

    public Integer getEmployeeNo() {
        return employeeNo;
    }

    public int getWorkYearMonth() {
        return workYearMonth;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
