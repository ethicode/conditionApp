package com.socgen.unibank.services.core.gateways.outbound;

import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;
import com.socgen.unibank.services.core.model.DerogatoryConditionRequest;
import java.util.List;
import java.util.Optional;

public interface DerogatoryConditionRequestRepositoryGateway {
    DerogatoryConditionRequest save(DerogatoryConditionRequest request);

    Optional<DerogatoryConditionRequest> findById(String requestId);

    List<DerogatoryConditionRequest> findAll();

    List<DerogatoryConditionRequest> search(DerogatoryConditionRequestSearchCriteria criteria);
}
