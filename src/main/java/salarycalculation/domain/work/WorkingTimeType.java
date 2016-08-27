package salarycalculation.domain.work;

/**
 * 時間外労働の種類を表す列挙型。
 *
 * @author MASAYUKI
 */
public enum WorkingTimeType {

    /** 通常残業 */
    WORK_OVER(1),
    /** 深夜残業 */
    LATE_NIGHT_OVER(1.1),
    /** 休日労働 */
    HOLIDAY_WORK(1.2),
    /** 休日深夜 */
    HOLIDAY_LATE_NIGHT_OVER(1.3);
    private double rate;

    /**
     * コンストラクタ
     *
     * @param rate 時間が基準給与に対する倍率
     */
    private WorkingTimeType(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
