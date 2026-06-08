package com.socgen.unibank.services.gateway;

import com.socgen.unibank.platform.springboot.config.ProxyEndpoints;
import com.socgen.unibank.services.gateway.inbound.DerogatoryConditionDashboardController;
import com.socgen.unibank.services.gateway.inbound.DerogatoryConditionRequestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DerogatoryConditionGatewayBeansFactory {

    @Bean
    ProxyEndpoints createDerogatoryConditionRequestControllerEndpoints() {
        return ProxyEndpoints.create(DerogatoryConditionRequestController.class);
    }

    @Bean
    ProxyEndpoints createDerogatoryConditionDashboardControllerEndpoints() {
        return ProxyEndpoints.create(DerogatoryConditionDashboardController.class);
    }
}
