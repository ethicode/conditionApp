package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.core.gateways.outbound.DerogatoryConditionNotificationGateway;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DerogatoryConditionNotificationGatewayImpl implements DerogatoryConditionNotificationGateway {

    private final List<String> notifications = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void notify(String recipientRole, String subject, String message) {
        notifications.add(recipientRole + " | " + subject + " | " + message);
    }

    public List<String> getNotifications() {
        return List.copyOf(notifications);
    }
}
