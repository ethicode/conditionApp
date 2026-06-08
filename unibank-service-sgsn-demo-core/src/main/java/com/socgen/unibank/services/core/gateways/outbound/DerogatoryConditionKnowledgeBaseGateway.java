package com.socgen.unibank.services.core.gateways.outbound;

import java.util.List;

public interface DerogatoryConditionKnowledgeBaseGateway {
    List<String> searchProcedures(String keyword);
}
