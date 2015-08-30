package salarycalculation.exception;

import java.sql.SQLException;

/**
 * {@link SQLException}を実行時例外として扱うための例外クラス。
 *
 * @author naotake
 */
public class RuntimeSQLException extends RuntimeException {

    /** SerialVersionUID */
    private static final long serialVersionUID = 1L;

    public RuntimeSQLException(String message, Exception cause) {
        super(message, cause);
    }
}
