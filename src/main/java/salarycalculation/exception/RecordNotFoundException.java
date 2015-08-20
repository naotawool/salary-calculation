package salarycalculation.exception;

import java.util.Arrays;

/**
 * 主キーを基に検索を行い、対象データが存在しなかった事を表す例外クラス。
 *
 * @author naotake
 */
public class RecordNotFoundException extends RuntimeException {

    /** SerialVersionUID */
    private static final long serialVersionUID = 1L;
    /** 検索対象エンティティのクラス */
    private final Class<?> targetClass;
    /** 検索時に使用した主キー */
    private final Object[] keys;

    public RecordNotFoundException(Class<?> targetClass, Object... keys) {
        this.targetClass = targetClass;
        this.keys = keys;
    }

    /**
     * 検索対象エンティティのクラスを取得する。
     *
     * @return 検索対象エンティティのクラス
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * 検索時に使用した主キーを取得する。
     *
     * @return 検索時に使用した主キー
     */
    public Object[] getKey() {
        return keys;
    }

    /**
     * 検索時の情報を基に文字列表現を返す。
     *
     * @return 文字列表現
     */
    public String toString() {
        return String.format("%s(%s)%s", getClass().getSimpleName(),
                targetClass.getCanonicalName(), Arrays.toString(keys));
    }
}
