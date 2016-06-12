package salarycalculation.domain.employee;

import lombok.Getter;
import lombok.ToString;
import salarycalculation.utils.BaseValueObject;
import salarycalculation.utils.Money;

@Getter
@ToString
public class Capability extends BaseValueObject {

    /** 等級 */
    private final CapabilityRank rank;

    /** 金額 */
    private final Money amount;

    public Capability(CapabilityRank rank, Money amount) {
        this.rank = rank;
        this.amount = amount;
    }

    public static Capability pm(Money amount) {
        return new Capability(CapabilityRank.PM, amount);
    }

    public static Capability pl(Money amount) {

        return new Capability(CapabilityRank.PL, amount);
    }

    public static Capability normal(CapabilityRank rank, Money amount) {
        return new Capability(rank, amount);
    }

    public Money getSeparatedAllowance() {
        return rank.getSeparatedAllowance();
    }

    public boolean isManager() {
        return rank.isManager();
    }

}
