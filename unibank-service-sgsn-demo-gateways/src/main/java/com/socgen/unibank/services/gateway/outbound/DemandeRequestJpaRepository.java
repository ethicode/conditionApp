package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.gateway.outbound.entities.DemandeRequestEntity;
import java.util.List;
import java.util.Optional;

public interface DemandeRequestJpaRepository {
    DemandeRequestEntity save(DemandeRequestEntity entity);

    Optional<DemandeRequestEntity> findById(String requestId);

    List<DemandeRequestEntity> findAll();

    List<DemandeRequestEntity> search(DemandeRequestSearchCriteria criteria);
}
