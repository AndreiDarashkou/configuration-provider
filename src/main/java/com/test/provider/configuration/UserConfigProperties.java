package com.test.provider.configuration;

import com.test.provider.model.ConfigurationType;
import com.test.provider.model.UserConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "user-config")
public class UserConfigProperties extends HashMap<ConfigurationType, UserConfiguration> {
}
