package com.test.provider.controller;

import com.test.provider.model.UserConfiguration;
import com.test.provider.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.test.provider.controller.ConfigurationController.URL;

@RestController
@RequestMapping(URL)
@RequiredArgsConstructor
public class ConfigurationController {

    public static final String URL = "/api/config";

    private final ConfigurationService service;

    @GetMapping("get/{userId}")
    public Mono<UserConfiguration> get(@PathVariable("userId") String userId) {
        return service.get(userId);
    }

}