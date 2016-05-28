package salarycalculation.utils;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Money extends BaseValueObject {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal amount;

    public static Money from(long amount) {
        return from(BigDecimal.valueOf(amount));
    }

    public static Money from(BigDecimal amount) {
        return new Money(amount);
    }

    private Money(BigDecimal amount) {
        Objects.requireNonNull(amount);
        this.amount = amount;
    }

    public Money add(Money money) {
        return new Money(amount.add(money.amount));
    }

    public Money multiply(double rate) {
        return Money.from(amount.multiply(BigDecimal.valueOf(rate)));
    }

    public Money minus(Money money) {
        return Money.from(amount.subtract(money.amount));
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean isGraterThan(Money assumption) {
        return this.amount.compareTo(assumption.amount) > 0;
    }

}
