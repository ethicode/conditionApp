package com.socgen.unibank.services.gateway;

import com.socgen.unibank.platform.springboot.config.ProxyEndpoints;
import com.socgen.unibank.services.gateway.inbound.DemandeDashboardController;
import com.socgen.unibank.services.gateway.inbound.DemandeRequestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemandeGatewayBeansFactory {

    @Bean
    ProxyEndpoints createDemandeRequestControllerEndpoints() {
        return ProxyEndpoints.create(DemandeRequestController.class);
    }

    @Bean
    ProxyEndpoints createDemandeDashboardControllerEndpoints() {
        return ProxyEndpoints.create(DemandeDashboardController.class);
    }
}
