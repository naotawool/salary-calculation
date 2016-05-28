package salarycalculation.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import salarycalculation.utils.Money;

/**
 * 勤続手当のEnum
 *
 * @author MASAYUKI
 *
 */
public enum LongServiceAllowance {
    /** ３年手当 */
    THREE(3, Money.from(3000)),
    /** ５年手当 */
    FIVE(5, Money.from(5000)),
    /** １０年手当 */
    TEN(10, Money.from(10000)),
    /** ２０年手当 */
    TWENTY(20, Money.from(20000));

    /** 手当額 */
    private final Money allowance;

    /** 勤続年数 */
    private final int attendanceYear;

    private LongServiceAllowance(int attendaceYear, Money allowance) {
        this.allowance = allowance;
        this.attendanceYear = attendaceYear;
    }

    public Money allowance() {
        return allowance;
    }

    /**
     * 勤続月数が手当がもらえる月数だった場合は勤続手当を返却する
     *
     * @param attendanceMonth 勤続月数
     * @return 勤続手当がもらえる月数だったら該当する勤続手当、そうでない場合はEmpty
     */
    public static Optional<LongServiceAllowance> determineTargetAsOpt(int attendanceMonth) {

        int durationYear = (attendanceMonth / 12);

        Predicate<LongServiceAllowance> isCorrespondingYear = e -> e.attendanceYear == durationYear;
        Predicate<LongServiceAllowance> isJustFirstMonth = e -> attendanceMonth % 12 == 0;

        return Arrays.stream(values())
                .filter(isCorrespondingYear.and(isJustFirstMonth))
                .findFirst();
    }

}
