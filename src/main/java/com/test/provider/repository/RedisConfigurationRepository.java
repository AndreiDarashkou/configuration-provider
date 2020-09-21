package com.test.provider.repository;

import com.test.provider.model.ConfigurationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RedisConfigurationRepository implements ConfigurationRepository {

    private final ReactiveValueOperations<String, ConfigurationType> configOperations;

    @Override
    public Mono<ConfigurationType> save(String userId, ConfigurationType type) {
        return configOperations
                .setIfAbsent(userId, type)
                .flatMap(res -> res ? Mono.just(type) : find(userId));
    }

    @Override
    public Mono<ConfigurationType> find(String userId) {
        return configOperations.get(userId);
    }

}
