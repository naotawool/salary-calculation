package salarycalculation.domain;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKey;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import salarycalculation.database.CapabilityDao;
import salarycalculation.database.EmployeeDao;
import salarycalculation.database.OrganizationDao;
import salarycalculation.database.RoleDao;
import salarycalculation.entity.Capability;
import salarycalculation.entity.Employee;
import salarycalculation.entity.Organization;
import salarycalculation.entity.Role;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link EmployeeRepository}に対する EasyMock を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeRepositoryTest_EasyMock {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private EmployeeRepository testee;
    private EmployeeDao mockDao;
    private OrganizationDao mockOrganizationDao;
    private RoleDao mockRoleDao;
    private CapabilityDao mockCapabilityDao;

    private Employee entity;
    private Organization organization;
    private Role role;
    private Capability capability;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        mockDao = createMock(EmployeeDao.class);
        mockOrganizationDao = createMock(OrganizationDao.class);
        mockRoleDao = createMock(RoleDao.class);
        mockCapabilityDao = createMock(CapabilityDao.class);

        testee = new EmployeeRepository();
        testee.setDao(mockDao);
        testee.setOrganizationDao(mockOrganizationDao);
        testee.setRoleDao(mockRoleDao);
        testee.setCapabilityDao(mockCapabilityDao);
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
        expect(mockDao.countByOrganization(code)).andReturn(8L).times(2);

        // 再生
        replay(mockDao);

        // 実行
        assertThat(testee.countByOrganization(code), is(5L));
        assertThat(testee.countByOrganization(code), is(6L));
        assertThat(testee.countByOrganization(code), is(7L));
        assertThat(testee.countByOrganization(code), is(8L));
        assertThat(testee.countByOrganization(code), is(8L));

        // 検証
        verify(mockDao);
    }

    @Test
    public void 社員と関連する情報も合わせて取得できること() {
        String no = "101";
        String organization = "ORGANIZATION2";
        String role = "ROLE3";
        String capability = "CAPABILITY4";

        this.entity = createEntity(no, organization, role, capability);

        // 振る舞いを定義
        expect(mockDao.get(no)).andReturn(this.entity);
        expect(mockOrganizationDao.get(organization)).andReturn(this.organization);
        expect(mockRoleDao.get(role)).andReturn(this.role);
        expect(mockCapabilityDao.get(capability)).andReturn(this.capability);

        // 再生モードへ
        replay(mockDao, mockOrganizationDao, mockRoleDao, mockCapabilityDao);

        // 実行
        EmployeeDomain actual = testee.get(no);

        // 検証
        assertThat(actual.getEntity(), sameInstance(this.entity));
        assertThat(actual.getOrganization(), sameInstance(this.organization));
        assertThat(actual.getRole(), sameInstance(this.role));
        assertThat(actual.getCapability(), sameInstance(this.capability));

        // 振る舞いの検証
        verify(mockDao, mockOrganizationDao, mockRoleDao, mockCapabilityDao);
    }

    @Test
    public void 社員に紐付く組織情報取得時に例外を発生させるテスト() {
        String no = "101";
        String organization = "ORGANIZATION2";

        this.entity = createEntity(no, organization, "", "");
        RecordNotFoundException expectException = createException(Organization.class, organization);

        // 振る舞いを定義
        expect(mockDao.get(no)).andReturn(this.entity);
        expect(mockOrganizationDao.get(organization)).andThrow(expectException);

        // 再生モードへ
        replay(mockDao, mockOrganizationDao);

        // 期待する例外内容
        expected.expect(RecordNotFoundException.class);
        expected.expect(isClass(Organization.class));
        expected.expect(isKey(organization));

        // 実行
        try {
            testee.get(no);
        } finally {
            verify(mockDao, mockOrganizationDao);
        }
    }

    @Test
    public void 意図した順番で呼び出し回数に応じて取得できる社員数が変化すること() {
        String code1 = "TEST1";
        String code2 = "TEST2";
        String code3 = "TEST3";

        // 呼び出し順序を厳密に検証するモック
        mockDao = createStrictMock(EmployeeDao.class);
        testee.setDao(mockDao);

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

    @Test
    public void 社員と関連する情報を意図した順番で呼び出して取得できること() {
        String no = "101";
        String organization = "ORGANIZATION2";
        String role = "ROLE3";
        String capability = "CAPABILITY4";

        this.entity = createEntity(no, organization, role, capability);

        // 振る舞いを定義
        expect(mockDao.get(no)).andReturn(this.entity);
        expect(mockOrganizationDao.get(organization)).andReturn(this.organization);
        expect(mockRoleDao.get(role)).andReturn(this.role);
        expect(mockCapabilityDao.get(capability)).andReturn(this.capability);

        // 再生モードへ
        replay(mockDao, mockOrganizationDao, mockRoleDao, mockCapabilityDao);

        // 実行
        EmployeeDomain actual = testee.get(no);

        // 検証
        assertThat(actual.getEntity(), sameInstance(this.entity));
        assertThat(actual.getOrganization(), sameInstance(this.organization));
        assertThat(actual.getRole(), sameInstance(this.role));
        assertThat(actual.getCapability(), sameInstance(this.capability));

        // 振る舞いの検証
        verify(mockDao, mockOrganizationDao, mockRoleDao, mockCapabilityDao);
    }

    private Employee createEntity(String no, String organization, String role, String capability) {
        Employee entity = new Employee();
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
