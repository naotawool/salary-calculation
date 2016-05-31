package salarycalculation.utils;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Money extends BaseValueObject {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal value;

    public static Money from(long amount) {
        return from(BigDecimal.valueOf(amount));
    }

    public static Money from(BigDecimal amount) {
        return new Money(amount.setScale(0, BigDecimal.ROUND_DOWN));
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

    public boolean isGraterThan(Money assumption) {
        return this.value.compareTo(assumption.value) > 0;
    }

}
