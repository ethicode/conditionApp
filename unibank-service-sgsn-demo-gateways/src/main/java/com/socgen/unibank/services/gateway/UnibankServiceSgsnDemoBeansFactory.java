package com.socgen.unibank.services.gateway;

import com.socgen.unibank.platform.springboot.config.ProxyEndpoints;
import com.socgen.unibank.services.gateway.inbound.UnibankServiceSgsnDemoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnibankServiceSgsnDemoBeansFactory {

    @Bean
    ProxyEndpoints createContentAPIEndpoints() {
        return ProxyEndpoints.create(UnibankServiceSgsnDemoEndpoint.class);
    }
}
