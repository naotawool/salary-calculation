package salarycalculation.domain.work;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import salarycalculation.utils.BaseValueObject;

/**
 * 時間外労働を表すVO。
 *
 * @author MASAYUKI
 */
@AllArgsConstructor
@Getter
@ToString
public class WorkingOverUnit extends BaseValueObject {

    /** 時間外労働時間 */
    private final BigDecimal workingTime;

    /** 時間外労働のタイプ */
    private final WorkingTimeType workingTimeType;
}
