package salarycalculation.database;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKey;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import salarycalculation.entity.Organization;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link OrganizationDao}に対するテストクラス。
 *
 * @author naotake
 */
public class OrganizationDaoTest {

    private OrganizationDao testee;

    @Rule
    public ExpectedException expect = ExpectedException.none();

    /**
     * 事前処理。
     */
    @Before
    public void setUp() {
        testee = new OrganizationDao();
    }

    @Test
    public void 組織コードに一致した組織を取得できること() {
        Organization actual = testee.get("ODG1");
        assertThat(actual.getCode(), is("ODG1"));
        assertThat(actual.getName(), is("開発部1グループ"));
    }

    @Test
    public void 存在しない組織コードを指定した場合に例外が送出されること() {
        expect.expect(RecordNotFoundException.class);
        expect.expect(isClass(Organization.class));
        expect.expect(isKey("XX99"));

        testee.get("XX99");
    }
}
