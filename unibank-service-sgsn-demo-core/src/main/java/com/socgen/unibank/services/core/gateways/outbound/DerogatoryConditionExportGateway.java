package com.socgen.unibank.services.core.gateways.outbound;

import com.socgen.unibank.services.core.model.DerogatoryConditionRequest;
import java.util.List;

public interface DerogatoryConditionExportGateway {
    String exportRequests(List<DerogatoryConditionRequest> requests);
}
