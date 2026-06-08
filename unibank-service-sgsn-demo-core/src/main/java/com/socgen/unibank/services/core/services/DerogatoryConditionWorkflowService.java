package com.socgen.unibank.services.core.services;

import com.socgen.unibank.services.api.model.DerogatoryConditionDashboardResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCloseRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCreateRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestReturnRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestUpdateRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestValidationRequest;
import com.socgen.unibank.services.core.gateways.outbound.DerogatoryConditionExportGateway;
import com.socgen.unibank.services.core.gateways.outbound.DerogatoryConditionNotificationGateway;
import com.socgen.unibank.services.core.gateways.outbound.DerogatoryConditionRequestRepositoryGateway;
import com.socgen.unibank.services.core.model.DerogatoryConditionRequest;
import com.socgen.unibank.services.core.model.DerogatoryConditionRequestStatus;
import java.time.Duration;
import java.time.Instant;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class DerogatoryConditionWorkflowService {

    private final DerogatoryConditionRequestRepositoryGateway requestRepository;
    private final DerogatoryConditionNotificationGateway notificationGateway;
    private final DerogatoryConditionExportGateway exportGateway;

    public DerogatoryConditionRequestResponse create(DerogatoryConditionRequestCreateRequest input) {
        DerogatoryConditionRequest request = new DerogatoryConditionRequest();
        request.setRequestId("DCR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        request.setCustomerReference(input.getCustomerReference());
        request.setRequestType(input.getRequestType());
        request.setMessage(input.getMessage());
        request.setStatus(DerogatoryConditionRequestStatus.NEW_REQUEST);
        request.setCurrentActor("ACE");
        request.setCreatedAt(Instant.now());
        request.setUpdatedAt(request.getCreatedAt());
        if (input.getAttachmentNames() != null) {
            input.getAttachmentNames().forEach(request::addAttachment);
        }
        request.addHistory("Demande creee par ACE");
        requestRepository.save(request);
        notificationGateway.notify("Charge de clientele", "Nouvelle demande", request.getRequestId());
        return toResponse(request);
    }

    public DerogatoryConditionRequestResponse update(DerogatoryConditionRequestUpdateRequest input) {
        DerogatoryConditionRequest request = load(input.getRequestId());
        ensureMutable(request);
        if (StringUtils.hasText(input.getMessage())) {
            request.setMessage(input.getMessage());
        }
        if (StringUtils.hasText(input.getComment())) {
            request.setComment(input.getComment());
            request.addHistory(input.getComment());
        }
        if (input.getAttachmentNames() != null) {
            input.getAttachmentNames().forEach(request::addAttachment);
        }
        request.touch();
        requestRepository.save(request);
        return toResponse(request);
    }

    public DerogatoryConditionRequestResponse validate(DerogatoryConditionRequestValidationRequest input) {
        DerogatoryConditionRequest request = load(input.getRequestId());
        DerogatoryConditionRequestStatus targetStatus = DerogatoryConditionRequestStatus.fromValue(input.getTargetStatus());
        request.setStatus(targetStatus);
        request.setCurrentActor(roleForStatus(targetStatus));
        request.setComment(input.getComment());
        if (StringUtils.hasText(input.getComment())) {
            request.addHistory(input.getComment());
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(roleForStatus(targetStatus), "Workflow update", request.getRequestId());
        return toResponse(request);
    }

    public DerogatoryConditionRequestResponse returnRequest(DerogatoryConditionRequestReturnRequest input) {
        DerogatoryConditionRequest request = load(input.getRequestId());
        DerogatoryConditionRequestStatus targetStatus = StringUtils.hasText(input.getTargetStatus())
            ? DerogatoryConditionRequestStatus.fromValue(input.getTargetStatus())
            : DerogatoryConditionRequestStatus.WAITING_FOR_CORRECTION;
        request.setStatus(targetStatus);
        request.setCurrentActor(roleForStatus(targetStatus));
        request.setComment(StringUtils.hasText(input.getComment()) ? input.getComment() : input.getReason());
        if (StringUtils.hasText(request.getComment())) {
            request.addHistory(request.getComment());
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(roleForStatus(targetStatus), "Demande retournee", request.getRequestId());
        return toResponse(request);
    }

    public DerogatoryConditionRequestResponse assign(DerogatoryConditionRequestAssignmentRequest input) {
        DerogatoryConditionRequest request = load(input.getRequestId());
        request.setCurrentActor(input.getAssignee());
        request.setStatus(DerogatoryConditionRequestStatus.APPROVED);
        request.setComment(input.getComment());
        request.addHistory("Affecte a " + input.getAssignee());
        if (StringUtils.hasText(input.getComment())) {
            request.addHistory(input.getComment());
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(input.getAssignee(), "Demande affectee", request.getRequestId());
        return toResponse(request);
    }

    public DerogatoryConditionRequestResponse close(DerogatoryConditionRequestCloseRequest input) {
        DerogatoryConditionRequest request = load(input.getRequestId());
        request.setStatus(input.isApproved() ? DerogatoryConditionRequestStatus.CLOSED : DerogatoryConditionRequestStatus.IN_PROGRESS);
        request.setCurrentActor(input.isApproved() ? "Metier ACE" : "Agent SIOP");
        request.setComment(input.getComment());
        if (StringUtils.hasText(input.getComment())) {
            request.addHistory(input.getComment());
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(request.getCurrentActor(), "Cloture workflow", request.getRequestId());
        return toResponse(request);
    }

    public DerogatoryConditionRequestSearchResponse search(DerogatoryConditionRequestSearchCriteria input) {
        List<DerogatoryConditionRequestResponse> items = requestRepository.search(input).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        DerogatoryConditionRequestSearchResponse response = new DerogatoryConditionRequestSearchResponse();
        response.setItems(items);
        response.setTotalCount(items.size());
        return response;
    }

    public String export(DerogatoryConditionRequestSearchCriteria input) {
        return exportGateway.exportRequests(requestRepository.search(input));
    }

    public DerogatoryConditionDashboardResponse dashboard() {
        List<DerogatoryConditionRequest> requests = requestRepository.findAll();
        DerogatoryConditionDashboardResponse response = new DerogatoryConditionDashboardResponse();
        response.setTotalRequests(requests.size());
        response.setResolvedRequests(requests.stream().filter(request -> request.getStatus() == DerogatoryConditionRequestStatus.RESOLVED).count());
        response.setClosedRequests(requests.stream().filter(request -> request.getStatus() == DerogatoryConditionRequestStatus.CLOSED).count());
        response.setRequestsByStatus(countByStatus(requests));
        response.setTasksByRole(countByRole(requests));
        response.setAverageLeadTimeHours(averageLeadTimeHours(requests));
        response.setAverageReturnTimeHours(averageReturnTimeHours(requests));
        return response;
    }

    private DerogatoryConditionRequest load(String requestId) {
        return requestRepository.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Unknown request id: " + requestId));
    }

    private void ensureMutable(DerogatoryConditionRequest request) {
        if (request.getStatus() != null && request.getStatus().isTerminal()) {
            throw new IllegalStateException("Request is already closed: " + request.getRequestId());
        }
    }

    private DerogatoryConditionRequestResponse toResponse(DerogatoryConditionRequest request) {
        DerogatoryConditionRequestResponse response = new DerogatoryConditionRequestResponse();
        response.setRequestId(request.getRequestId());
        response.setCustomerReference(request.getCustomerReference());
        response.setRequestType(request.getRequestType());
        response.setStatus(request.getStatus() == null ? null : request.getStatus().name());
        response.setCurrentActor(request.getCurrentActor());
        response.setMessage(request.getMessage());
        response.setComment(request.getComment());
        response.setCreatedAt(request.getCreatedAt());
        response.setUpdatedAt(request.getUpdatedAt());
        response.setAttachmentNames(List.copyOf(request.getAttachmentNames()));
        response.setHistory(List.copyOf(request.getHistory()));
        return response;
    }

    private Map<String, Long> countByStatus(List<DerogatoryConditionRequest> requests) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (DerogatoryConditionRequestStatus status : DerogatoryConditionRequestStatus.values()) {
            counts.put(status.name(), 0L);
        }
        for (DerogatoryConditionRequest request : requests) {
            String key = request.getStatus() == null ? "UNKNOWN" : request.getStatus().name();
            counts.put(key, counts.getOrDefault(key, 0L) + 1L);
        }
        return counts;
    }

    private Map<String, Long> countByRole(List<DerogatoryConditionRequest> requests) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (DerogatoryConditionRequest request : requests) {
            String role = request.getCurrentActor() == null ? "UNASSIGNED" : request.getCurrentActor();
            counts.put(role, counts.getOrDefault(role, 0L) + 1L);
        }
        return counts;
    }

    private double averageLeadTimeHours(List<DerogatoryConditionRequest> requests) {
        return requests.stream()
            .filter(request -> request.getCreatedAt() != null && request.getUpdatedAt() != null)
            .mapToLong(request -> Duration.between(request.getCreatedAt(), request.getUpdatedAt()).toMinutes())
            .average()
            .orElse(0.0) / 60.0;
    }

    private double averageReturnTimeHours(List<DerogatoryConditionRequest> requests) {
        return requests.stream()
            .filter(request -> request.getHistory() != null && !request.getHistory().isEmpty())
            .mapToLong(request -> request.getHistory().size())
            .average()
            .orElse(0.0);
    }

    private String roleForStatus(DerogatoryConditionRequestStatus status) {
        if (status == null) {
            return "ACE";
        }
        switch (status) {
            case WAITING_RM_VALIDATION:
                return "Charge de clientele";
            case WAITING_DCE_VALIDATION:
                return "Responsable de marche";
            case DECLARED:
            case APPROVED:
            case WAITING_SIOP_VALIDATION:
                return "Agent SIOP";
            case IN_VALIDATION:
                return "Valideur SIOP RUN";
            case VALIDATED:
                return "Metier ACE";
            case RESOLVED:
                return "ACE / Referent metier";
            default:
                return "ACE";
        }
    }
}
