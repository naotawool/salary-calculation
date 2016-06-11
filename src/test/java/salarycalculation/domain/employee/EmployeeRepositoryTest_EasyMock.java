package salarycalculation.domain.employee;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import salarycalculation.database.CapabilityDao;
import salarycalculation.database.EmployeeDao;
import salarycalculation.database.RoleDao;
import salarycalculation.database.WorkDao;
import salarycalculation.database.model.CapabilityRecord;
import salarycalculation.database.model.EmployeeRecord;
import salarycalculation.database.model.OrganizationRecord;
import salarycalculation.database.model.RoleRecord;
import salarycalculation.database.repository.EmployeeRepositoryDao;
import salarycalculation.database.repository.EmployeeTransformer;
import salarycalculation.domain.organization.Organization;
import salarycalculation.domain.organization.OrganizationRepository;
import salarycalculation.exception.RecordNotFoundException;
import salarycalculation.utils.PersonName;

/**
 * {@link EmployeeRepository}に対する EasyMock を使ったテストクラス。
 *
 * @author naotake
 */
public class EmployeeRepositoryTest_EasyMock {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private EmployeeRepositoryDao testee;
    private EmployeeDao mockDao;
    private OrganizationRepository mockOrganizationRepository;
    private RoleDao mockRoleDao;
    private CapabilityDao mockCapabilityDao;
    private WorkDao mockWorkDao;

    private EmployeeRecord entity;
    private Organization organization;
    private RoleRecord role;
    private CapabilityRecord capability;

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        mockDao = createMock(EmployeeDao.class);
        mockOrganizationRepository = createMock(OrganizationRepository.class);
        mockRoleDao = createMock(RoleDao.class);
        mockCapabilityDao = createMock(CapabilityDao.class);
        mockWorkDao = createMock(WorkDao.class);

        EmployeeTransformer transformer = new EmployeeTransformer();

        transformer.setRoleDao(mockRoleDao);
        transformer.setCapabilityDao(mockCapabilityDao);
        transformer.setOrganizationRepository(mockOrganizationRepository);

        testee = new EmployeeRepositoryDao();
        testee.setDao(mockDao);
        testee.setOrganizationDao(mockOrganizationRepository);
        testee.setTransFormer(transformer);
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
        testee = createNiceMock(EmployeeRepositoryDao.class);

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
        this.organization = new Organization("organizationCode", "organizationName");

        this.role = new RoleRecord();
        this.role.setAmount(100);
        this.role.setRank("roleRank");

        this.capability = new CapabilityRecord();
        this.capability.setRank("AS");

        // 振る舞いを定義
        expect(mockDao.get(no)).andReturn(this.entity);
        expect(mockOrganizationRepository.find(organization)).andReturn(this.organization);
        expect(mockRoleDao.get(role)).andReturn(this.role);
        expect(mockCapabilityDao.get(capability)).andReturn(this.capability);
        expect(mockWorkDao.findAll(Integer.valueOf(no))).andReturn(Collections.emptyList());

        // 再生モードへ
        replay(mockDao, mockOrganizationRepository, mockRoleDao, mockCapabilityDao, mockWorkDao);

        // 実行
        Employee actual = testee.get(no);

        // 検証
        assertThat(actual.getName(), is(equalTo(new PersonName(this.entity.getName()))));
        assertThat(actual.getOrganization(), is(equalTo(new Organization("organizationCode", "organizationName"))));
        assertThat(actual.getRole().getRank(), is(equalTo("roleRank")));
        assertThat(actual.getCapability().getRank(), sameInstance(CapabilityRank.AS));
        assertThat(actual.getWorkTimes(), is(nullValue()));

        // 振る舞いの検証
        verify(mockDao, mockOrganizationRepository, mockRoleDao, mockCapabilityDao);
    }

    @Test
    public void 社員に紐付く組織情報取得時に例外を発生させるテスト() {
        String no = "101";
        String organization = "ORGANIZATION2";

        this.entity = createEntity(no, organization, "", "");
        RecordNotFoundException expectException = createException(OrganizationRecord.class, organization);

        // 振る舞いを定義
        expect(mockDao.get(no)).andReturn(this.entity);
        expect(mockOrganizationRepository.find(organization)).andThrow(expectException);

        // 再生モードへ
        replay(mockDao, mockOrganizationRepository);

        // 期待する例外内容
        expected.expect(RecordNotFoundException.class);
        expected.expect(isClass(OrganizationRecord.class));
        expected.expect(isKey(organization));

        // 実行
        try {
            testee.get(no);
        } finally {
            verify(mockDao, mockOrganizationRepository);
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

        this.organization = new Organization("organizationCode", "organizationName");

        this.role = new RoleRecord();
        this.role.setAmount(100);
        this.role.setRank("roleRank");

        this.capability = new CapabilityRecord();
        this.capability.setRank("AS");

        // 振る舞いを定義
        expect(mockDao.get(no)).andReturn(this.entity);
        expect(mockOrganizationRepository.find(organization)).andReturn(this.organization);
        expect(mockRoleDao.get(role)).andReturn(this.role);
        expect(mockCapabilityDao.get(capability)).andReturn(this.capability);
        expect(mockWorkDao.findAll(Integer.valueOf(no))).andReturn(Collections.emptyList());

        // 再生モードへ
        replay(mockDao, mockOrganizationRepository, mockRoleDao, mockCapabilityDao, mockWorkDao);

        // 実行
        Employee actual = testee.get(no);

        // 検証
        assertThat(actual.getName(), is(equalTo(new PersonName(this.entity.getName()))));
        assertThat(actual.getOrganization(), is(equalTo(new Organization("organizationCode", "organizationName"))));
        assertThat(actual.getRole().getRank(), is(equalTo("roleRank")));
        assertThat(actual.getCapability().getRank(), sameInstance(CapabilityRank.AS));
        assertThat(actual.getWorkTimes(), is(nullValue()));

        // 振る舞いの検証
        verify(mockDao, mockOrganizationRepository, mockRoleDao, mockCapabilityDao);
    }

    private EmployeeRecord createEntity(String no, String organization, String role, String capability) {
        EmployeeRecord entity = new EmployeeRecord();
        entity.setNo(Integer.valueOf(no));
        entity.setBirthday(Date.valueOf(LocalDate.ofEpochDay(0)));
        entity.setJoinDate(Date.valueOf(LocalDate.ofEpochDay(0)));
        entity.setOrganization(organization);
        entity.setRoleRank(role);
        entity.setCapabilityRank(capability);
        return entity;
    }

    private RecordNotFoundException createException(Class<?> clazz, String key) {
        return new RecordNotFoundException(clazz, key);
    }
}
