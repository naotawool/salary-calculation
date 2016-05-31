package salarycalculation.domain.employee;

import salarycalculation.utils.BaseValueObject;
import salarycalculation.utils.Money;

public class Role extends BaseValueObject {

    /** 等級 */
    private final String rank;

    /** 金額 */
    private final Money amount;

    public Role(String rank, Money amount) {
        this.rank = rank;
        this.amount = amount;
    }

    public String getRank() {
        return rank;
    }

    public Money getAmount() {
        return amount;
    }

}
