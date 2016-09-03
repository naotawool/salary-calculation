package salarycalculation.database;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.destination.DriverManagerDestination.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.operation.Operation;

import salarycalculation.database.model.RoleRecord;
import salarycalculation.dbsetup.RoleSetupSupport;
import salarycalculation.exception.RecordNotFoundException;

/**
 * {@link RoleDao}に対するテストクラス。
 *
 * @author naotake
 */
public class RoleDaoTest implements RoleSetupSupport {

    private RoleDao testee;
    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    /**
     * 事前処理。
     */
    @Before
    public void setUp() throws Exception {
        testee = new RoleDao();

        String url = "jdbc:h2:./data/salary_calculation_test";
        Connection connection = DriverManager.getConnection(url, "sa", "");
        Whitebox.setInternalState(testee, "connection", connection);

        // 事前データの準備
        Operation truncate = deleteAllFrom("work", "employee", "role", "capability", "organization");
        Operation role = roleInsert();

        // 事前データ投入
        dbSetupTracker.launchIfNecessary(new DbSetup(with("jdbc:h2:./data/salary_calculation_test", "sa", ""),
                                                     sequenceOf(truncate, role)));
    }

    @Test
    public void 引数が_null_の場合_NullPointerException_が発生すること() {
        dbSetupTracker.skipNextLaunch();

        // when
        Throwable thrown = catchThrowable(() -> {
            testee.get(null);
        });

        // expect
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void 引数が空文字の場合_NullPointerException_が発生すること() {
        dbSetupTracker.skipNextLaunch();

        // when
        Throwable thrown = catchThrowable(() -> {
            testee.get("");
        });

        // expect
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void 引数が2桁より少ない場合_IllegalArgumentException_が発生すること() {
        dbSetupTracker.skipNextLaunch();

        // when
        Throwable thrown = catchThrowable(() -> {
            testee.get("A");
        });

        // expect
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                          .hasMessage("等級は 2 桁で指定してください[1]");
    }

    @Test
    public void 引数が2桁より大きい場合_IllegalArgumentException_が発生すること() {
        dbSetupTracker.skipNextLaunch();

        // when
        Throwable thrown = catchThrowable(() -> {
            testee.get("123");
        });

        // expect
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                          .hasMessage("等級は 2 桁で指定してください[3]");
    }

    @Test
    public void 存在しない等級を指定した場合に例外が送出されること() {
        dbSetupTracker.skipNextLaunch();

        // when
        Throwable thrown = catchThrowable(() -> {
            testee.get("XX");
        });

        // expect
        final TargetKeys keysXX = new TargetKeys("XX");
        assertThat(thrown).isInstanceOf(RecordNotFoundException.class).is(targetRole).is(keysXX);
    }

    private final Condition<Throwable> targetRole = new Condition<Throwable>("target class") {

        public boolean matches(Throwable actual) {
            RecordNotFoundException casted = RecordNotFoundException.class.cast(actual);
            return (casted.getTargetClass() == RoleRecord.class);
        }
    };

    private static final class TargetKeys extends Condition<Throwable> {

        private final Object[] expects;

        public TargetKeys(Object... expects) {
            // as(Arrays.toString(expects));
            this.expects = expects;
        }

        public boolean matches(Throwable actual) {
            RecordNotFoundException casted = RecordNotFoundException.class.cast(actual);
            return Arrays.equals(casted.getKey(), expects);
        }
    }
}
