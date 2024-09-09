package com.tripleS.server.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {"com.tripleS.server"})
@Configuration
public class OpenFeignConfig {

}