package salarycalculation.domain.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import salarycalculation.utils.Money;

/**
 * 能力等級の種類を表す列挙型。
 *
 * @author MASAYUKI
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CapabilityRank {
    PL(Money.from(10000), true), PM(Money.from(30000), true), SE(), PG(), AS();

    private Money separatedAllowance;
    private boolean isManager;

    private CapabilityRank() {
        this(Money.ZERO, false);
    }
}
