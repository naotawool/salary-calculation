package salarycalculation.domain.work;

import lombok.Data;

/**
 * 勤務時間のID.<br/>
 * サロゲートキーが望ましい.
 *
 * @author MASAYUKI
 */
@Data
public class WorkOverTimeId {

    private final Integer employeeNo;
    /** 稼動年月 */
    private final int workYearMonth;
}
