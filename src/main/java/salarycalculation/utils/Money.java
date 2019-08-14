package salarycalculation.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * お金を表すVO。<br />
 * 円とかの通貨単位もつといい感じになる予定。
 *
 * @author MASAYUKI
 */
public class Money extends BaseValueObject {

    /** 0円 */
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal value;

    public static Money from(long amount) {
        return from(BigDecimal.valueOf(amount));
    }

    public static Money from(BigDecimal amount) {
        return new Money(amount.setScale(0, RoundingMode.DOWN));
    }

    private Money(BigDecimal amount) {
        Objects.requireNonNull(amount);
        this.value = amount;
    }

    public Money add(Money money) {
        return new Money(value.add(money.value));
    }

    public Money multiply(double rate) {
        return Money.from(value.multiply(BigDecimal.valueOf(rate)));
    }

    public Money minus(Money money) {

        return Money.from(value.subtract(money.value));
    }

    public BigDecimal value() {
        return this.value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean isGraterThan(Money target) {

        return this.value.compareTo(target.value) > 0;
    }
}
