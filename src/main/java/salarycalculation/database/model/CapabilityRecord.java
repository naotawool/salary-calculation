package salarycalculation.database.model;

/**
 * 能力等級を表す Entity。
 *
 * @author naotake
 */
public class CapabilityRecord {

    /** 等級 */
    private String rank;

    /** 金額 */
    private int amount;

    /**
     * 等級を取得する。
     *
     * @return 等級
     */
    public String getRank() {
        return rank;
    }

    /**
     * 等級を設定する。
     *
     * @param rank 等級
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * 金額を取得する。
     *
     * @return 金額
     */
    public int getAmount() {
        return amount;
    }

    /**
     * 金額を設定する。
     *
     * @param amount 金額
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
