package com.tripleS.server.global.config;

import com.tripleS.server.user.service.type.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
public class ConfigurationPropsConfig {
}
