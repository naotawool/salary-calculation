package salarycalculation.web.resources;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.FixtureHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import salarycalculation.web.Application;
import salarycalculation.web.configuration.SalaryCalculationConfiguration;

/**
 * {@link EmployeeResource}に対する結合テストクラス.
 *
 * @author naotake
 */
public class EmployeeResourceIntegrationTest {

    @ClassRule
    public static DropwizardAppRule<SalaryCalculationConfiguration> appRule = new DropwizardAppRule<>(Application.class,
                                                                                                      "salary-calculation.yml");

    @Test
    public void 従業員の情報を一件取得できること() throws Exception {
        Client client = JerseyClientBuilder.newClient();
        String url = String.format("http://localhost:%d/employee?no=1", appRule.getLocalPort());

        Response response = client.target(url).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo(fixture("get"));
    }

    @Test
    public void 存在しない従業員の情報を取得しようとした場合に404が返却されること() throws Exception {
        Client client = JerseyClientBuilder.newClient();
        String url = String.format("http://localhost:%d/employee?no=9", appRule.getLocalPort());

        Response response = client.target(url).request().get();

        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void 全ての従業員情報を取得できること() throws Exception {
        Client client = JerseyClientBuilder.newClient();
        String url = String.format("http://localhost:%d/employee/list", appRule.getLocalPort());

        Response response = client.target(url).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo(fixture("list"));
    }

    private String fixture(String expect) {
        String filePath = EmployeeResourceIntegrationTest.class.getCanonicalName().replace('.', '/')
                          + "-expect-" + expect + ".json";
        return FixtureHelpers.fixture(filePath);
    }
}
