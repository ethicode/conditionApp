package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.gateway.outbound.entities.DemandeRequestAttachmentEntity;
import java.util.List;

public interface DemandeRequestAttachmentJpaRepository {
    DemandeRequestAttachmentEntity saveAttachment(String requestId, DemandeRequestAttachmentEntity attachment);

    List<DemandeRequestAttachmentEntity> findAttachmentsByRequestId(String requestId);
}
