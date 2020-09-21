package com.test.provider.service;

import com.test.provider.model.ConfigurationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationTypeProvider {

    @Value("${type-a-chance}")
    private double typeAChance;

    public ConfigurationType get() {
        return Math.random() < typeAChance ? ConfigurationType.A : ConfigurationType.B;
    }
}
