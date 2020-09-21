package com.test.provider.repository;

import com.test.provider.model.ConfigurationType;
import reactor.core.publisher.Mono;

public interface ConfigurationRepository {

    Mono<ConfigurationType> save(String userId, ConfigurationType type);

    Mono<ConfigurationType> find(String userId);

}
