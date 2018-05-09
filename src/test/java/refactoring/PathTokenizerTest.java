package refactoring;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import refactoring.PathTokenizer;

/**
 * {@link PathTokenizer}に対するテストクラス。
 */
public class PathTokenizerTest {

    private PathTokenizer testee;

    @Before
    public void before() throws Exception {
        testee = new PathTokenizer();
    }

    @Test
    public void パスから末尾のIDを取得できること() throws Exception {
        String path = "http://foo.bar/user/123";
        long actual = testee.getEntityId(path);
        assertThat(actual).isEqualTo(123);
    }

    @Test
    public void パスから一覧アクセスかどうかを判定できること() throws Exception {
        // 一覧アクセスの場合
        String path = "http://foo.bar/user/";
        boolean actual = testee.isListAccess(path);
        assertThat(actual).isTrue();

        // 個別アクセスの場合
        path = "http://foo.bar/user/123";
        actual = testee.isListAccess(path);
        assertThat(actual).isFalse();
    }

    @Test
    public void セキュアアクセスかどうかを判定できること() throws Exception {
        // セキュアアクセスの場合
        String path = "https://foo.bar/user/";
        boolean actual = testee.isSecureAccess(path);
        assertThat(actual).isTrue();

        // 非セキュアアクセスの場合
        path = "http://foo.bar/user/";
        actual = testee.isSecureAccess(path);
        assertThat(actual).isFalse();
    }
}
