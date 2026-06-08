package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;
import com.socgen.unibank.services.gateway.outbound.entities.DerogatoryConditionRequestEntity;
import java.util.List;
import java.util.Optional;

public interface DerogatoryConditionRequestJpaRepository {
    DerogatoryConditionRequestEntity save(DerogatoryConditionRequestEntity entity);

    Optional<DerogatoryConditionRequestEntity> findById(String requestId);

    List<DerogatoryConditionRequestEntity> findAll();

    List<DerogatoryConditionRequestEntity> search(DerogatoryConditionRequestSearchCriteria criteria);
}
