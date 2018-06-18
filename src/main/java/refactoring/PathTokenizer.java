package refactoring;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * URL パスを分解するためのクラス。
 */
public class PathTokenizer {

    private static final String SECURE_PROTOCOL = "https";
    private static final String URL_SEPARATOR = "/";

    /**
     * パスの末尾に含まれる ID を取得する。<br />
     * e.g. http://foo.bar/user/123 から 123 を抽出
     *
     * @param path パス
     * @return ID
     */
    public long getEntityId(String path) {
        String[] tokens = splitPath(path);
        String id = tokens[tokens.length - 1];
        return Long.parseLong(id);
    }

    /**
     * パスの末尾が<code>/</code>かどうかを判定する。
     *
     * @param path パス
     * @return 末尾が<code>/</code>の場合は true
     */
    public boolean isListAccess(String path) {
        String[] tokens = splitPath(path);
        String lastToken = tokens[tokens.length - 1];
        return !(NumberUtils.isCreatable(lastToken));
    }

    private String[] splitPath(String path) {
        return path.split(URL_SEPARATOR);
    }

    /**
     * パスのプロトコルが<code>https</code>かどうかを判定する。
     *
     * @param path パス
     * @return プロトコルが<code>https</code>の場合は true
     */
    public boolean isSecureAccess(String path) {
        return path.startsWith(SECURE_PROTOCOL);
    }
}
