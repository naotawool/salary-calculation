package salarycalculation.web.resources;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WiremockLearningTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options().port(8081));

    @Test
    public void Wiremockの振る舞いに定義したURLにアクセスできること() throws Exception {
        // Wiremock の振る舞いを定義
        stubFor(get(urlEqualTo("/employee_list")).willReturn(
            aResponse().withStatus(200).withHeader("Content-Type", "text/plain").withBody("Hello world!")));

        // アクセス
        URL url = new URL("http://localhost:8081/employee_list");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 検証
        assertThat(connection.getResponseCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }
}
