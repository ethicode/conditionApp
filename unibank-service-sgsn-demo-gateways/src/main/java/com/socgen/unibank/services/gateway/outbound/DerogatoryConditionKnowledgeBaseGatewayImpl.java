package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.core.gateways.outbound.DerogatoryConditionKnowledgeBaseGateway;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class DerogatoryConditionKnowledgeBaseGatewayImpl implements DerogatoryConditionKnowledgeBaseGateway {

    @Override
    public List<String> searchProcedures(String keyword) {
        String normalized = keyword == null ? "" : keyword.toLowerCase(Locale.ROOT);
        return List.of(
            "Procedure de creation d une demande",
            "Procedure de validation metier",
            "Procedure de traitement SIOP",
            "Procedure de cloture du dossier"
        ).stream()
            .filter(item -> normalized.isBlank() || item.toLowerCase(Locale.ROOT).contains(normalized))
            .toList();
    }
}
