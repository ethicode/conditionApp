package com.socgen.unibank.services.core.gateways.outbound;

import com.socgen.unibank.services.core.model.DemandeRequest;
import java.util.List;

public interface DemandeExportGateway {
    String exportRequests(List<DemandeRequest> requests);
}
