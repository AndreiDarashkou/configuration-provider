package com.test.provider.configuration;

import com.test.provider.model.ConfigurationType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisOperations<String, ConfigurationType> redisOperations(ReactiveRedisConnectionFactory factory) {

        RedisSerializationContext.RedisSerializationContextBuilder<String, ConfigurationType> builder =
                RedisSerializationContext.newSerializationContext(RedisSerializer.string());

        var serializer = new Jackson2JsonRedisSerializer<>(ConfigurationType.class);
        RedisSerializationContext<String, ConfigurationType> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
