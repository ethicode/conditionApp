package com.socgen.unibank.services.core.gateways.outbound;

public interface DerogatoryConditionNotificationGateway {
    void notify(String recipientRole, String subject, String message);
}
