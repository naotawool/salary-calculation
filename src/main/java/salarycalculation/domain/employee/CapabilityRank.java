package salarycalculation.domain.employee;

import salarycalculation.utils.Money;

public enum CapabilityRank {
    PL(10000, true), PM(30000, true), SE(), PG(), AS();

    private Money separatedAllowance;
    private boolean isManager;

    private CapabilityRank(Money money, boolean isManager) {
        this.separatedAllowance = money;
        this.isManager = isManager;
    }

    private CapabilityRank(int amount, boolean isManager) {
        this(Money.from(amount), isManager);
    }

    private CapabilityRank() {
        this(Money.ZERO, false);
    }

    public Money getSeparatedAllowance() {
        return this.separatedAllowance;
    }

    public boolean isManager() {
        return isManager;
    }
}
