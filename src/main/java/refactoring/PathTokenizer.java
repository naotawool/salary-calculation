package refactoring;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * URL パスを分解するためのクラス。
 */
public class PathTokenizer {

    /**
     * パスの末尾に含まれる ID を取得する。<br />
     * e.g. http://foo.bar/user/123 から 123 を抽出
     *
     * @param path パス
     * @return ID
     */
    public long getEntityId(String path) {
        String[] splitted = path.split("/");
        return Long.parseLong(splitted[splitted.length - 1]);
    }

    /**
     * パスの末尾が<code>/</code>かどうかを判定する。
     *
     * @param path パス
     * @return 末尾が<code>/</code>の場合は true
     */
    public boolean isListAccess(String path) {
        String[] splitted = path.split("/");
        return !(NumberUtils.isCreatable(splitted[splitted.length - 1]));
    }

    /**
     * パスのプロトコルが<code>https</code>かどうかを判定する。
     *
     * @param path パス
     * @return プロトコルが<code>https</code>の場合は true
     */
    public boolean isSecureAccess(String path) {
        return path.startsWith("https");
    }
}
