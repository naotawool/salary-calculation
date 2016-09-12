package unittest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import unittest.Employee.Department;

/**
 * {@link Employee}に対するテストクラス。
 *
 * @author naotake
 */
public class EmployeeTest {

    private Employee testee;

    @Before
    public void setUp() throws Exception {
        testee = new Employee(123, LocalDate.of(1987, 7, 18));
        testee.setName("Taro", "Tanaka");
    }

    @Test
    public void 設定した氏名を正しく取得できること() throws Exception {
        testee.setName("Jiro", "Tanaka");

        // 検証
        assertThat(testee.getName()).isEqualTo("Jiro Tanaka");
        assertThat(testee.getFirstName()).isEqualTo("Jiro");
        assertThat(testee.getLastName()).isEqualTo("Tanaka");
    }

    /**
     * {@link Employee#calcAge()}は結果が不定になるためテスト対象外とする。
     */
    @Test
    public void 指定年月日時点での年齢が正しく計算されること() throws Exception {
        assertThat(testee.calcAge(LocalDate.of(2016, 7, 17))).isEqualTo(28);
        assertThat(testee.calcAge(LocalDate.of(2016, 7, 18))).isEqualTo(29);
        assertThat(testee.calcAge(LocalDate.of(2016, 7, 19))).isEqualTo(29);
    }

    @Test
    public void 部署判定が正しく行われること() throws Exception {
        testee.setDepartment(Department.DEVELOPMENT);

        // 検証
        assertThat(testee.isSameDepartment(Department.DEVELOPMENT)).isTrue();
        assertThat(testee.isSameDepartment(Department.FINANCE)).isFalse();
    }

    @Test
    public void 従業員番号を使ってequalsが正しく動作こと() throws Exception {
        // 同一番号の場合
        Employee target = new Employee(123, LocalDate.of(1982, 8, 6));
        assertThat(testee).isEqualTo(target);

        // 異なる番号の場合
        target = new Employee(1234, LocalDate.of(1987, 7, 18));
        assertThat(testee).isNotEqualTo(target);
    }

    @Test
    public void 期待する文字列表現を取得できること() throws Exception {
        assertThat(testee).hasToString("[00123]Taro Tanaka");
    }
}
