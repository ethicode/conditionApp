package com.socgen.unibank.services.core.gateways.outbound;

import java.util.List;

public interface DemandeKnowledgeBaseGateway {
    List<String> searchProcedures(String keyword);
}
