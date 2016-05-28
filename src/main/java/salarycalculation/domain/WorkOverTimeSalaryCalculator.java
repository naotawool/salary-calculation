package salarycalculation.domain;

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

    public WorkOverTimeSalaryCalculator(Money workOverTime1hAmount) {
        this.workOverTime1hAmount = workOverTime1hAmount;
        this.totalMoney = Money.ZERO;
    }

    public WorkOverTimeSalaryCalculator bind(WorkingOverUnit workingTimes) {
        this.bind(workingTimes.getWorkingTime(), workingTimes.getWorkingTimeType().getRate());
        return this;
    }

    public WorkOverTimeSalaryCalculator bind(BigDecimal workTime, double rate) {

        BigDecimal raw = BigDecimal.valueOf(workOverTime1hAmount.getAmount().intValue() * rate);
        BigDecimal totalMoney = raw.multiply(workTime);
        this.totalMoney = this.totalMoney.add(Money.from(totalMoney));

        return this;
    }

    public Money calculate() {
        return totalMoney;
    }

    public static WorkOverTimeSalaryCalculator create(Money workOverTime1hAmount) {
        return new WorkOverTimeSalaryCalculator(workOverTime1hAmount);
    }

}
