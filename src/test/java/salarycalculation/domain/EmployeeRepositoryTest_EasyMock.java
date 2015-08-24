package salarycalculation.domain;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import salarycalculation.database.EmployeeDao;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link EmployeeRepository}に対する EasyMock を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeRepositoryTest_EasyMock {

    private EmployeeRepository testee;
    private EmployeeDao mockDao;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        mockDao = createMock(EmployeeDao.class);
        testee = new EmployeeRepository();
        testee.setDao(mockDao);
    }

    @Test
    public void 組織コードに該当する社員数を取得できること() {
        String code = "TEST1";

        // 振る舞いを定義
        expect(mockDao.countByOrganization(code)).andReturn(5L);

        // 再生
        replay(mockDao);

        // 実行
        assertThat(testee.countByOrganization(code), is(5L));

        // 検証
        verify(mockDao);
    }

    @Test
    public void 複数の組織コードに該当する社員数を取得できること() {
        String code1 = "TEST1";
        String code2 = "TEST2";
        String code3 = "TEST3";

        // 振る舞いを定義
        expect(mockDao.countByOrganization(code1)).andReturn(5L);
        expect(mockDao.countByOrganization(code2)).andReturn(7L);
        expect(mockDao.countByOrganization(code3)).andReturn(3L);

        // 再生
        replay(mockDao);

        // 実行
        assertThat(testee.countByOrganization(code1), is(5L));
        assertThat(testee.countByOrganization(code2), is(7L));
        assertThat(testee.countByOrganization(code3), is(3L));

        // 検証
        verify(mockDao);
    }

    @Ignore("verify で失敗します")
    @Test
    public void 予期せぬ組織コードの振る舞いが呼ばれた場合に0を取得できること() {
        String code = "TEST1";

        // ゆるいモックを用意
        testee = createNiceMock(EmployeeRepository.class);

        // 振る舞いを定義
        expect(mockDao.countByOrganization(code)).andReturn(5L);

        // 再生
        replay(mockDao);

        // 実行
        assertThat(testee.countByOrganization("TEST99"), is(0L));

        // 検証
        // Mockito のようにメソッド単位の検証が出来ないため
        // 振る舞い定義したメソッドが呼ばれていない場合、テスト失敗とみなされる
        verify(mockDao);
    }

    @Test
    public void 呼び出し回数に応じて取得できる社員数が変化すること() {
        String code = "TEST1";

        // 振る舞いを定義
        expect(mockDao.countByOrganization(code)).andReturn(5L).andReturn(6L).andReturn(7L);

        // 再生
        replay(mockDao);

        // 実行
        assertThat(testee.countByOrganization(code), is(5L));
        assertThat(testee.countByOrganization(code), is(6L));
        assertThat(testee.countByOrganization(code), is(7L));

        // 検証
        verify(mockDao);
    }

    private RecordNotFoundException createException(Class<?> clazz, String key) {
        return new RecordNotFoundException(clazz, key);
    }
}
