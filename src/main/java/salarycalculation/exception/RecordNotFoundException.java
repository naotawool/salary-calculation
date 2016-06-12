package salarycalculation.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * 主キーを基に検索を行い、対象データが存在しなかった事を表す例外クラス。
 *
 * @author naotake
 */
@Getter
@ToString
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
}
