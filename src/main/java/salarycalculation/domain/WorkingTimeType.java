package salarycalculation.domain;

public enum WorkingTimeType {
    NORMAL(1), WORK_OVER(1), LATE_NIGHT_OVER(1.1), HOLIDAY_WORK(1.2), HOLIDAY_LATE_NIGHT_OVER(1.3);
    private double rate;

    private WorkingTimeType(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

}
