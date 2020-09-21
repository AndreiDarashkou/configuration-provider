package com.test.provider.controller;

import static com.test.provider.controller.ConfigurationController.URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.test.provider.model.UserConfiguration;
import com.test.provider.service.ConfigurationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ConfigurationController.class)
public class ConfigurationControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private ConfigurationService service;
    @MockBean
    private ReactiveRedisConnectionFactory connectionFactory;

    @Test
    public void shouldCallServiceGetMethod() {
        String testConfigValue = "test value";

        when(service.get("1")).thenReturn(Mono.just(new UserConfiguration(testConfigValue)));

        webClient.get()
                .uri(URL + "/get/1")
                .exchange()
                .expectBody()
                .jsonPath("$.value")
                .value(val -> assertThat(val).isEqualTo(testConfigValue));
    }

}