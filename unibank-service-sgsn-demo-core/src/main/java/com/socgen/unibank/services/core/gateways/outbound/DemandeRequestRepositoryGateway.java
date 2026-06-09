package com.socgen.unibank.services.core.gateways.outbound;

import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.core.model.DemandeRequest;
import java.util.List;
import java.util.Optional;

public interface DemandeRequestRepositoryGateway {
    DemandeRequest save(DemandeRequest request);

    Optional<DemandeRequest> findById(String requestId);

    boolean deleteById(String requestId);

    List<DemandeRequest> findAll();

    List<DemandeRequest> search(DemandeRequestSearchCriteria criteria);
}
