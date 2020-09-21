package com.test.provider.controller;

import com.test.provider.model.ConfigurationType;
import com.test.provider.model.UserConfiguration;
import com.test.provider.repository.ConfigurationRepository;
import com.test.provider.service.ConfigurationService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.test.provider.controller.ConfigurationController.URL;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebFluxTest(ConfigurationController.class)
public class ConfigurationControllerTest {

    @Autowired
    private WebTestClient webClient;
    @Autowired
    private ConfigurationService service;
    @Autowired
    private ConfigurationRepository repository;

    @Autowired
    private ReactiveRedisOperations<String, ConfigurationType> redisOperations;

    @Test
    public void shouldReturnSavedConfiguration() {
        StepVerifier.create(repository.save("1", ConfigurationType.B))
                .expectNext(ConfigurationType.B)
                .verifyComplete();

        webClient.get()
                .uri(URL + "/get/1")
                .exchange()
                .expectBody()
                .jsonPath("$.value")
                .value(val -> assertThat(val).isEqualTo("B value"));
    }

    @Test
    public void shouldReturnTheSameConfiguration() {
        UserConfiguration userConfig = getUserConfiguration().block();
        assertThat(userConfig).isNotNull();

        StepVerifier.create(getUserConfiguration(), 10)
                .expectNext(userConfig)
                .verifyComplete();
    }

    private Mono<UserConfiguration> getUserConfiguration() {
        return webClient.get()
                .uri(URL + "/get/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(UserConfiguration.class)
                .getResponseBody()
                .single();
    }

    @After
    public void cleanRedis() {
        redisOperations.opsForValue().delete(String.valueOf(1));
    }

}