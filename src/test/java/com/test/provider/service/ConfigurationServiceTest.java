package com.test.provider.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.test.provider.ConfigurationProviderApplication;
import com.test.provider.model.ConfigurationType;
import com.test.provider.model.UserConfiguration;
import com.test.provider.repository.ConfigurationRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationProviderApplication.class)
public class ConfigurationServiceTest {

    private static final String USER_ID = "1";

    @Autowired
    private ConfigurationService service;
    @Autowired
    private ConfigurationRepository repository;
    @Autowired
    private UserConfigurationProvider configurationProvider;
    @Autowired
    private ReactiveValueOperations<String, ConfigurationType> configOperations;

    @Test
    public void shouldReturnSavedConfiguration() {
        StepVerifier.create(repository.save(USER_ID, ConfigurationType.B))
                .expectNext(ConfigurationType.B)
                .verifyComplete();

        StepVerifier.create(service.get(USER_ID))
                .expectNext(configurationProvider.get(ConfigurationType.B))
                .verifyComplete();
    }

    @Test
    public void shouldReturnTheSameConfiguration() {
        UserConfiguration userConfig = service.get(USER_ID).block();
        assertThat(userConfig).isNotNull();

        StepVerifier.create(service.get(USER_ID))
                .expectNext(userConfig)
                .verifyComplete();
    }

    @After
    public void cleanRedis() {
        configOperations.delete(USER_ID).block();
    }
}
