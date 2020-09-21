package com.test.provider.service;

import com.test.provider.configuration.UserConfigProperties;
import com.test.provider.model.ConfigurationType;
import com.test.provider.model.UserConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConfigurationProvider {

    private final UserConfigProperties propertyMap;

    public UserConfiguration get(ConfigurationType configurationType) {
        return propertyMap.get(configurationType);
    }

}