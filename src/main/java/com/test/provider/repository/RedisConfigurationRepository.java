package com.test.provider.repository;

import com.test.provider.model.ConfigurationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RedisConfigurationRepository implements ConfigurationRepository {

    private final ReactiveRedisOperations<String, ConfigurationType> redisOperations;

    @Override
    public Mono<ConfigurationType> save(String userId, ConfigurationType type) {
        return redisOperations.opsForValue()
                .setIfAbsent(userId, type)
                .flatMap(res -> res ? Mono.just(type) : find(userId));
    }

    @Override
    public Mono<ConfigurationType> find(String userId) {
        return redisOperations.opsForValue().get(userId);
    }

}
