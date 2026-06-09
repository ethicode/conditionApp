package com.socgen.unibank.services.gateway.outbound;

import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.core.gateways.outbound.DemandeRequestRepositoryGateway;
import com.socgen.unibank.services.core.model.DemandeRequest;
import com.socgen.unibank.services.core.model.DemandeRequestStatus;
import com.socgen.unibank.services.gateway.outbound.entities.DemandeRequestAttachmentEntity;
import com.socgen.unibank.services.gateway.outbound.entities.DemandeRequestEntity;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DemandeRequestRepositoryGatewayImpl implements DemandeRequestRepositoryGateway {

    private final Map<String, DemandeRequestEntity> storage = new ConcurrentHashMap<>();
    private final Map<String, List<DemandeRequestAttachmentEntity>> attachmentsByRequestId = new ConcurrentHashMap<>();

    @PostConstruct
    void initSampleData() {
        if (!storage.isEmpty()) {
            return;
        }

        save(createSampleRequest(
            "DCR-0001",
            "CLI-001245",
            "Montant",
            "Demande d'augmentation temporaire de plafond.",
            "Charge de clientele",
            DemandeRequestStatus.WAITING_RM_VALIDATION,
            Instant.now().minusSeconds(5 * 24 * 3600L)
        ));

        save(createSampleRequest(
            "DCR-0002",
            "CLI-008991",
            "Echeance",
            "Besoin d'extension d'echeance suite a un incident de flux.",
            "Agent SIOP",
            DemandeRequestStatus.IN_VALIDATION,
            Instant.now().minusSeconds(3 * 24 * 3600L)
        ));

        save(createSampleRequest(
            "DCR-0003",
            "CLI-004422",
            "Garanties",
            "Mise a jour des garanties pour dossier prioritaire.",
            "Metier ACE",
            DemandeRequestStatus.RESOLVED,
            Instant.now().minusSeconds(24 * 3600L)
        ));
    }

    private DemandeRequest createSampleRequest(
        String requestId,
        String customerReference,
        String requestType,
        String message,
        String actor,
        DemandeRequestStatus status,
        Instant createdAt
    ) {
        DemandeRequest request = new DemandeRequest();
        request.setRequestId(requestId);
        request.setCustomerReference(customerReference);
        request.setRequestType(requestType);
        request.setMessage(message);
        request.setComment("Donnee de demonstration pour tests locaux");
        request.setCurrentActor(actor);
        request.setStatus(status);
        request.setCreatedAt(createdAt);
        request.setUpdatedAt(createdAt.plusSeconds(4 * 3600L));
        request.addAttachment("piece-jointe-" + requestId.toLowerCase() + ".pdf");
        request.addHistory("Demande initialisee en environnement local");
        return request;
    }

    @Override
    public DemandeRequest save(DemandeRequest request) {
        DemandeRequestEntity entity = DemandeRequestEntity.fromDomain(request);
        storage.put(entity.getRequestId(), entity);
        attachmentsByRequestId.put(entity.getRequestId(), new ArrayList<>(entity.getAttachments()));
        return entity.toDomain();
    }

    @Override
    public Optional<DemandeRequest> findById(String requestId) {
        return Optional.ofNullable(storage.get(requestId)).map(DemandeRequestEntity::toDomain);
    }

    @Override
    public boolean deleteById(String requestId) {
        DemandeRequestEntity removed = storage.remove(requestId);
        attachmentsByRequestId.remove(requestId);
        return removed != null;
    }

    @Override
    public List<DemandeRequest> findAll() {
        return storage.values().stream().map(DemandeRequestEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<DemandeRequest> search(DemandeRequestSearchCriteria criteria) {
        return storage.values().stream()
            .map(DemandeRequestEntity::toDomain)
            .filter(request -> matches(criteria, request))
            .collect(Collectors.toList());
    }

    public DemandeRequestAttachmentEntity saveAttachment(String requestId, DemandeRequestAttachmentEntity attachment) {
        attachmentsByRequestId.computeIfAbsent(requestId, key -> new ArrayList<>()).add(attachment);
        DemandeRequestEntity entity = storage.get(requestId);
        if (entity != null) {
            entity.getAttachments().add(attachment);
        }
        return attachment;
    }

    public List<DemandeRequestAttachmentEntity> findAttachmentsByRequestId(String requestId) {
        return attachmentsByRequestId.getOrDefault(requestId, Collections.emptyList());
    }

    private boolean matches(DemandeRequestSearchCriteria criteria, DemandeRequest request) {
        if (criteria == null) {
            return true;
        }
        boolean match = true;
        if (StringUtils.hasText(criteria.getRequestId())) {
            match &= criteria.getRequestId().equalsIgnoreCase(request.getRequestId());
        }
        if (StringUtils.hasText(criteria.getCustomerReference())) {
            match &= containsIgnoreCase(request.getCustomerReference(), criteria.getCustomerReference());
        }
        if (StringUtils.hasText(criteria.getRequestType())) {
            match &= containsIgnoreCase(request.getRequestType(), criteria.getRequestType());
        }
        if (StringUtils.hasText(criteria.getStatus())) {
            match &= request.getStatus() != null && request.getStatus().name().equalsIgnoreCase(criteria.getStatus())
                || request.getStatus() != null && request.getStatus().getLabel().equalsIgnoreCase(criteria.getStatus());
        }
        if (StringUtils.hasText(criteria.getCurrentActor())) {
            match &= containsIgnoreCase(request.getCurrentActor(), criteria.getCurrentActor());
        }
        if (StringUtils.hasText(criteria.getKeyword())) {
            match &= containsIgnoreCase(request.getMessage(), criteria.getKeyword())
                || containsIgnoreCase(request.getComment(), criteria.getKeyword())
                || containsIgnoreCase(request.getRequestId(), criteria.getKeyword());
        }
        if (criteria.getFromDate() != null && request.getCreatedAt() != null) {
            LocalDate created = request.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate();
            match &= !created.isBefore(criteria.getFromDate());
        }
        if (criteria.getToDate() != null && request.getCreatedAt() != null) {
            LocalDate created = request.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate();
            match &= !created.isAfter(criteria.getToDate());
        }
        return match;
    }

    private boolean containsIgnoreCase(String value, String searched) {
        return value != null && searched != null && value.toLowerCase().contains(searched.toLowerCase());
    }
}
