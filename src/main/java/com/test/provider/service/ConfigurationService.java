package com.test.provider.service;

import com.test.provider.model.UserConfiguration;
import com.test.provider.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository repository;
    private final ConfigurationTypeProvider typeProvider;
    private final UserConfigurationProvider configurationProvider;

    public Mono<UserConfiguration> get(String userId) {
        return repository.find(userId)
                .switchIfEmpty(repository.save(userId, typeProvider.get()))
                .map(configurationProvider::get);
    }
}