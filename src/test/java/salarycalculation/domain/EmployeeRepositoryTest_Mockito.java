package salarycalculation.domain;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKey;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import salarycalculation.database.CapabilityDao;
import salarycalculation.database.EmployeeDao;
import salarycalculation.database.OrganizationDao;
import salarycalculation.database.RoleDao;
import salarycalculation.entity.CapabilityRecord;
import salarycalculation.entity.EmployeeRecord;
import salarycalculation.entity.OrganizationRecord;
import salarycalculation.entity.RoleRecord;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link EmployeeRepository}に対する Mockito を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeRepositoryTest_Mockito {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @InjectMocks
    private EmployeeRepository testee;
    @Mock
    private EmployeeDao mockDao;
    @Mock
    private OrganizationDao mockOrganizationDao;
    @Mock
    private RoleDao mockRoleDao;
    @Mock
    private CapabilityDao mockCapabilityDao;

    private EmployeeRecord entity;
    private OrganizationRecord organization;
    private RoleRecord role;
    private CapabilityRecord capability;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        organization = new OrganizationRecord();
        role = new RoleRecord();
        capability = new CapabilityRecord();
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

    @Test
    public void 社員と組織の情報を取得できること() {
        String no = "101";
        String organization = "ORGANIZATION2";

        this.entity = createEntity(no, organization, null, null);

        // 振る舞いを定義
        when(mockDao.get(no)).thenReturn(this.entity);
        when(mockOrganizationDao.get(organization)).thenReturn(this.organization);

        // 実行
        Employee actual = testee.getSimple(no);

        // 検証
        assertThat(actual.getName().getFullName(), is(equalTo(this.entity.getName())));
        assertThat(actual.getOrganization(), sameInstance(this.organization));

        // 振る舞いの検証
        verify(mockDao).get(no);
        verify(mockOrganizationDao).get(organization);
    }

    @Test
    public void 社員と関連する情報も合わせて取得できること() {
        String no = "101";
        String organization = "ORGANIZATION2";
        String role = "ROLE3";
        String capability = "CAPABILITY4";

        this.entity = createEntity(no, organization, role, capability);

        // 振る舞いを定義
        when(mockDao.get(no)).thenReturn(this.entity);
        when(mockOrganizationDao.get(organization)).thenReturn(this.organization);
        when(mockRoleDao.get(role)).thenReturn(this.role);
        when(mockCapabilityDao.get(capability)).thenReturn(this.capability);

        // 実行
        Employee actual = testee.get(no);

        // 検証
        assertThat(actual.getName().getFullName(), is(equalTo(this.entity.getName())));
        assertThat(actual.getOrganization(), sameInstance(this.organization));
        assertThat(actual.getRole(), sameInstance(this.role));
        assertThat(actual.getCapability(), sameInstance(this.capability));

        // 振る舞いの検証
        verify(mockDao).get(no);
        verify(mockOrganizationDao).get(organization);
        verify(mockRoleDao).get(role);
        verify(mockCapabilityDao).get(capability);
    }

    @Test
    public void 社員に紐付く組織情報取得時に例外を発生させるテスト() {
        String no = "101";
        String organization = "ORGANIZATION2";

        this.entity = createEntity(no, organization, "", "");
        RecordNotFoundException expectException = createException(OrganizationRecord.class, organization);

        // 振る舞いを定義
        when(mockDao.get(no)).thenReturn(this.entity);
        when(mockOrganizationDao.get(organization)).thenThrow(expectException);

        // 期待する例外内容
        expected.expect(RecordNotFoundException.class);
        expected.expect(isClass(OrganizationRecord.class));
        expected.expect(isKey(organization));

        // 実行
        try {
            testee.get(no);
        } finally {
            verify(mockDao).get(no);
            verify(mockOrganizationDao).get(organization);
        }
    }

    private EmployeeRecord createEntity(String no, String organization, String role, String capability) {
        EmployeeRecord entity = new EmployeeRecord();
        entity.setNo(Integer.valueOf(no));
        entity.setOrganization(organization);
        entity.setRoleRank(role);
        entity.setCapabilityRank(capability);
        return entity;
    }

    private RecordNotFoundException createException(Class<?> clazz, String key) {
        return new RecordNotFoundException(clazz, key);
    }
}
