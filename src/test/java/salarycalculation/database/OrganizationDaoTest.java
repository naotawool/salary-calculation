package salarycalculation.database;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.destination.DriverManagerDestination.with;
import static org.assertj.core.api.Assertions.assertThat;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isClass;
import static salarycalculation.matchers.RecordNotFoundExceptionMatcher.isKey;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.util.reflection.Whitebox;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.operation.Operation;

import salarycalculation.database.model.OrganizationRecord;
import salarycalculation.dbsetup.OrganizationSetupSupport;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link OrganizationDao}に対するテストクラス。
 *
 * @author naotake
 */
public class OrganizationDaoTest implements OrganizationSetupSupport {

    private OrganizationDao testee;
    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Rule
    public ExpectedException expect = ExpectedException.none();

    /**
     * 事前処理。
     */
    @Before
    public void setUp() throws Exception {
        testee = new OrganizationDao();

        String url = "jdbc:h2:./data/salary_calculation_test";
        Connection connection = DriverManager.getConnection(url, "sa", "");
        Whitebox.setInternalState(testee, "connection", connection);

        // 事前データの準備
        Operation truncate = deleteAllFrom("work", "employee", "role", "capability", "organization");
        Operation organization = organizationInsert();

        // 事前データ投入
        dbSetupTracker.launchIfNecessary(new DbSetup(with("jdbc:h2:./data/salary_calculation_test", "sa", ""),
                                                     sequenceOf(truncate, organization)));
    }

    @Test
    public void 組織コードに一致した組織を取得できること() {
        dbSetupTracker.skipNextLaunch();

        OrganizationRecord actual = testee.get("DEV1");
        assertThat(actual.getCode()).isEqualTo("DEV1");
        assertThat(actual.getName()).isEqualTo("開発部1");
    }

    @Test
    public void 存在しない組織コードを指定した場合に例外が送出されること() {
        dbSetupTracker.skipNextLaunch();

        expect.expect(RecordNotFoundException.class);
        expect.expect(isClass(OrganizationRecord.class));
        expect.expect(isKey("XX99"));

        testee.get("XX99");
    }
}
