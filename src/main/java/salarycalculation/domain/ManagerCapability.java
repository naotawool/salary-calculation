package salarycalculation.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

/**
 * マネージャ職の能力等級を表す。DBに入れたい。
 *
 * @author MASAYUKI
 *
 */
public enum ManagerCapability {

    PL(BigDecimal.valueOf(10000)), PM(BigDecimal.valueOf(30000));

    private BigDecimal separateAllowance;

    private ManagerCapability(BigDecimal separateAllowance) {
        this.separateAllowance = separateAllowance;
    }

    public BigDecimal getSeparateAllowance() {
        return separateAllowance;
    }

    public static Optional<BigDecimal> allowance(String value) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(value))
                .findFirst()
                .map(e -> e.getSeparateAllowance());
    }

    public static boolean isManager(String rank) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(rank))
                .findFirst().isPresent();
    }

}
