package unittest;

import java.util.Objects;

/**
 * 金額情報を表すクラス。
 *
 * @author naotake
 */
public class Amount {

    private final long nontax;
    private final long tax;

    private Amount(long nontax, long tax) {
        this.nontax = nontax;
        this.tax = tax;
    }

    public static Amount of(long nontax, long tax) {
        return new Amount(nontax, tax);
    }

    /**
     * 金額の加算を行う。
     *
     * @param augend 加算額
     * @return 加算結果
     */
    public Amount add(Amount augend) {
        long nontax = this.nontax + augend.getNontax();
        long tax = this.tax + augend.getTax();
        return Amount.of(nontax, tax);
    }

    /**
     * 金額の減算を行う。
     *
     * @param subtrahend 減算額
     * @return 減算結果
     */
    public Amount subtract(Amount subtrahend) {
        long nontax = this.nontax - subtrahend.getNontax();
        long tax = this.tax + subtrahend.getTax();
        return Amount.of(nontax, tax);
    }

    /**
     * 金額の乗算を行う。
     *
     * @param multiplicand 乗算額
     * @return 乗算結果
     */
    public Amount multiply(Amount multiplicand) {
        long nontax = this.nontax * multiplicand.getNontax();
        long tax = this.tax * multiplicand.getTax();
        return Amount.of(nontax, tax);
    }

    /**
     * @return 税抜金額
     */
    public long getNontax() {
        return nontax;
    }

    /**
     * @return 消費税
     */
    public long getTax() {
        return tax;
    }

    /**
     * @return 税込金額
     */
    public long getPretax() {
        return nontax + tax;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nontax, tax);
    }

    @Override
    public boolean equals(Object other) {
        if (!Amount.class.isInstance(other)) {
            return false;
        }

        Amount target = Amount.class.cast(other);
        return (this.nontax == target.getPretax() && this.tax == target.getTax());
    }

    @Override
    public String toString() {
        return String.format("%,d円(税%,d円)", getPretax(), getTax());
    }
}
