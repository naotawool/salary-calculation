package salarycalculation.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import salarycalculation.database.EmployeeDao;

/**
 * {@link EmployeeRepository}に対する Mockito を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeRepositoryTest_Mockito {

    private EmployeeRepository testee;
    private EmployeeDao mockDao;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        mockDao = mock(EmployeeDao.class);
        testee = new EmployeeRepository();
        testee.setDao(mockDao);
    }

    @Test
    public void 組織コードに該当する社員数を取得できること() {
        String code = "TEST1";

        // 振る舞いを定義
        when(mockDao.countByOrganization(code)).thenReturn(5L);

        // 実行
        assertThat(testee.countByOrganization(code), is(5L));

        // 検証
        verify(mockDao).countByOrganization(code);
    }

    @Test
    public void 複数の組織コードに該当する社員数を取得できること() {
        String code1 = "TEST1";
        String code2 = "TEST2";
        String code3 = "TEST3";

        // 振る舞いを定義
        when(mockDao.countByOrganization(code1)).thenReturn(5L);
        when(mockDao.countByOrganization(code2)).thenReturn(7L);
        when(mockDao.countByOrganization(code3)).thenReturn(3L);

        // 実行
        assertThat(testee.countByOrganization(code1), is(5L));
        assertThat(testee.countByOrganization(code2), is(7L));
        assertThat(testee.countByOrganization(code3), is(3L));

        // 検証
        verify(mockDao).countByOrganization(code1);
        verify(mockDao).countByOrganization(code2);
        verify(mockDao).countByOrganization(code3);
    }

    @Test
    public void 予期せぬ組織コードの振る舞いが呼ばれた場合に0を取得できること() {
        String code = "TEST1";

        // 振る舞いを定義
        when(mockDao.countByOrganization(code)).thenReturn(5L);

        // 実行
        assertThat(testee.countByOrganization("TEST99"), is(0L));

        // 検証
        verify(mockDao, never()).countByOrganization(code); // 1度も呼ばれていないことを検証
        verify(mockDao).countByOrganization("TEST99");
    }

    @Test
    public void 呼び出し回数に応じて取得できる社員数が変化すること() {
        String code = "TEST1";

        // 振る舞いを定義
        when(mockDao.countByOrganization(code)).thenReturn(5L, 6L, 7L);
        // 下記でも OK
        // when(mockDao.countByOrganization(code)).thenReturn(5L).thenReturn(6L).thenReturn(7L);

        // 実行
        assertThat(testee.countByOrganization(code), is(5L));
        assertThat(testee.countByOrganization(code), is(6L));
        assertThat(testee.countByOrganization(code), is(7L));

        // 検証
        verify(mockDao, times(3)).countByOrganization(code);
    }
}
