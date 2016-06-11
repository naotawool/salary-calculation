package salarycalculation.domain.work;

import java.math.BigDecimal;

import salarycalculation.utils.Money;

/**
 * 時間外給与を計算するヘルパー
 *
 * @author MASAYUKI
 *
 */
public class WorkOverTimeSalaryCalculator {

    private Money workOverTime1hAmount;

    private Money totalMoney;

    /**
     * コンストラクタ
     *
     * @param workOverTime1hAmount 時間外労働一時間あたりの基準給与
     */
    private WorkOverTimeSalaryCalculator(Money workOverTime1hAmount) {
        this.workOverTime1hAmount = workOverTime1hAmount;
        this.totalMoney = Money.ZERO;
    }

    /**
     * 時間外の単位を追加する。
     *
     * @param workingTimeUnit
     * @return 計算機
     */
    public WorkOverTimeSalaryCalculator append(WorkingOverUnit workingTimeUnit) {
        this.append(workingTimeUnit.getWorkingTime(), workingTimeUnit.getWorkingTimeType().getRate());
        return this;
    }

    private WorkOverTimeSalaryCalculator append(BigDecimal workTime, double rate) {

        BigDecimal raw = BigDecimal.valueOf(workOverTime1hAmount.value().intValue() * rate);
        BigDecimal totalMoney = raw.multiply(workTime);
        this.totalMoney = this.totalMoney.add(Money.from(totalMoney));

        return this;
    }

    /**
     * 追加された時間外労働と各レートで計算する。
     *
     * @return 時間外給与の合計時間
     */
    public Money calculate() {
        return totalMoney;
    }

    public static WorkOverTimeSalaryCalculator create(Money workOverTime1hAmount) {
        return new WorkOverTimeSalaryCalculator(workOverTime1hAmount);
    }

}
