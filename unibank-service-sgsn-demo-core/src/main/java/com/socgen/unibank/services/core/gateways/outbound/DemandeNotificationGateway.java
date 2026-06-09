package com.socgen.unibank.services.core.gateways.outbound;

public interface DemandeNotificationGateway {
    void notify(String recipientRole, String subject, String message);
}
