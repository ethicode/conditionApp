package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.gateway.outbound.entities.DerogatoryConditionRequestAttachmentEntity;
import java.util.List;

public interface DerogatoryConditionRequestAttachmentJpaRepository {
    DerogatoryConditionRequestAttachmentEntity saveAttachment(String requestId, DerogatoryConditionRequestAttachmentEntity attachment);

    List<DerogatoryConditionRequestAttachmentEntity> findAttachmentsByRequestId(String requestId);
}
