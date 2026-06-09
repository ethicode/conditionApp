package com.socgen.unibank.services.core.services;

import com.socgen.unibank.services.api.model.DemandeCommentCreateRequest;
import com.socgen.unibank.services.api.model.DemandeCommentResponse;
import com.socgen.unibank.services.api.model.DemandeDashboardResponse;
import com.socgen.unibank.services.api.model.DemandeRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DemandeRequestCloseRequest;
import com.socgen.unibank.services.api.model.DemandeRequestCreateRequest;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.model.DemandeRequestReturnRequest;
import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.api.model.DemandeRequestSearchResponse;
import com.socgen.unibank.services.api.model.DemandeRequestUpdateRequest;
import com.socgen.unibank.services.api.model.DemandeRequestValidationRequest;
import com.socgen.unibank.services.core.gateways.outbound.DemandeExportGateway;
import com.socgen.unibank.services.core.gateways.outbound.DemandeNotificationGateway;
import com.socgen.unibank.services.core.gateways.outbound.DemandeRequestRepositoryGateway;
import com.socgen.unibank.services.core.model.DemandeComment;
import com.socgen.unibank.services.core.model.DemandeRequest;
import com.socgen.unibank.services.core.model.DemandeRequestStatus;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
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
public class DemandeWorkflowService {

    private final DemandeRequestRepositoryGateway requestRepository;
    private final DemandeNotificationGateway notificationGateway;
    private final DemandeExportGateway exportGateway;

    public DemandeRequestResponse create(DemandeRequestCreateRequest input) {
        DemandeRequest request = new DemandeRequest();
        request.setRequestId("DCR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        request.setCustomerReference(input.getCustomerReference());
        request.setRequestType(input.getRequestType());
        request.setMessage(input.getMessage());
        request.setStatus(DemandeRequestStatus.NEW_REQUEST);
        request.setCurrentActor("ACE");
        request.setCreatedAt(Instant.now());
        request.setUpdatedAt(request.getCreatedAt());
        if (input.getAttachmentNames() != null) {
            input.getAttachmentNames().forEach(request::addAttachment);
        }
        addWorkflowComment(request, "Demande creee par ACE", request.getCreatedAt());
        requestRepository.save(request);
        notificationGateway.notify("Charge de clientele", "Nouvelle demande", request.getRequestId());
        return toResponse(request);
    }

    public DemandeRequestResponse update(DemandeRequestUpdateRequest input) {
        DemandeRequest request = load(input.getRequestId());
        ensureMutable(request);
        if (StringUtils.hasText(input.getMessage())) {
            request.setMessage(input.getMessage());
        }
        if (StringUtils.hasText(input.getComment())) {
            request.setComment(input.getComment());
            addWorkflowComment(request, input.getComment(), null);
        }
        if (input.getAttachmentNames() != null) {
            input.getAttachmentNames().forEach(request::addAttachment);
        }
        request.touch();
        requestRepository.save(request);
        return toResponse(request);
    }

    public DemandeRequestResponse validate(DemandeRequestValidationRequest input) {
        DemandeRequest request = load(input.getRequestId());
        DemandeRequestStatus targetStatus = DemandeRequestStatus.fromValue(input.getTargetStatus());
        request.setStatus(targetStatus);
        request.setCurrentActor(roleForStatus(targetStatus));
        request.setComment(input.getComment());
        if (StringUtils.hasText(input.getComment())) {
            addWorkflowComment(request, input.getComment(), null);
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(roleForStatus(targetStatus), "Workflow update", request.getRequestId());
        return toResponse(request);
    }

    public DemandeRequestResponse returnRequest(DemandeRequestReturnRequest input) {
        DemandeRequest request = load(input.getRequestId());
        DemandeRequestStatus targetStatus = StringUtils.hasText(input.getTargetStatus())
            ? DemandeRequestStatus.fromValue(input.getTargetStatus())
            : DemandeRequestStatus.WAITING_FOR_CORRECTION;
        request.setStatus(targetStatus);
        request.setCurrentActor(roleForStatus(targetStatus));
        request.setComment(StringUtils.hasText(input.getComment()) ? input.getComment() : input.getReason());
        if (StringUtils.hasText(request.getComment())) {
            addWorkflowComment(request, request.getComment(), null);
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(roleForStatus(targetStatus), "Demande retournee", request.getRequestId());
        return toResponse(request);
    }

    public DemandeRequestResponse assign(DemandeRequestAssignmentRequest input) {
        DemandeRequest request = load(input.getRequestId());
        request.setCurrentActor(input.getAssignee());
        request.setStatus(DemandeRequestStatus.APPROVED);
        request.setComment(input.getComment());
        addWorkflowComment(request, "Affecte a " + input.getAssignee(), null);
        if (StringUtils.hasText(input.getComment())) {
            addWorkflowComment(request, input.getComment(), null);
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(input.getAssignee(), "Demande affectee", request.getRequestId());
        return toResponse(request);
    }

    public DemandeRequestResponse close(DemandeRequestCloseRequest input) {
        DemandeRequest request = load(input.getRequestId());
        request.setStatus(input.isApproved() ? DemandeRequestStatus.CLOSED : DemandeRequestStatus.IN_PROGRESS);
        request.setCurrentActor(input.isApproved() ? "Metier ACE" : "Agent SIOP");
        request.setComment(input.getComment());
        if (StringUtils.hasText(input.getComment())) {
            addWorkflowComment(request, input.getComment(), null);
        }
        request.touch();
        requestRepository.save(request);
        notificationGateway.notify(request.getCurrentActor(), "Cloture workflow", request.getRequestId());
        return toResponse(request);
    }

    public DemandeCommentResponse addComment(String requestId, DemandeCommentCreateRequest input) {
        if (input == null || !StringUtils.hasText(input.getComment())) {
            throw new IllegalArgumentException("Comment is required");
        }

        DemandeRequest request = load(requestId);
        String workflowStep = StringUtils.hasText(input.getWorkflowStep())
            ? normalizeWorkflowStep(input.getWorkflowStep())
            : (request.getStatus() == null ? "UNKNOWN" : request.getStatus().name());

        DemandeComment comment = new DemandeComment(workflowStep, request.getCurrentActor(), input.getComment().trim(), Instant.now());
        request.addComment(comment);
        request.setComment(comment.getComment());
        request.addHistory("[" + workflowStep + "] " + comment.getComment());
        request.touch();
        requestRepository.save(request);
        return toCommentResponse(comment);
    }

    public List<DemandeCommentResponse> listComments(String requestId) {
        DemandeRequest request = load(requestId);
        return request.getComments().stream()
            .sorted(Comparator.comparing(DemandeComment::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())))
            .map(this::toCommentResponse)
            .collect(Collectors.toList());
    }

    public DemandeRequestSearchResponse search(DemandeRequestSearchCriteria input) {
        List<DemandeRequestResponse> items = requestRepository.search(input).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        DemandeRequestSearchResponse response = new DemandeRequestSearchResponse();
        response.setItems(items);
        response.setTotalCount(items.size());
        return response;
    }

    public String export(DemandeRequestSearchCriteria input) {
        return exportGateway.exportRequests(requestRepository.search(input));
    }

    public DemandeDashboardResponse dashboard() {
        List<DemandeRequest> requests = requestRepository.findAll();
        DemandeDashboardResponse response = new DemandeDashboardResponse();
        response.setTotalRequests(requests.size());
        response.setResolvedRequests(requests.stream().filter(request -> request.getStatus() == DemandeRequestStatus.RESOLVED).count());
        response.setClosedRequests(requests.stream().filter(request -> request.getStatus() == DemandeRequestStatus.CLOSED).count());
        response.setRequestsByStatus(countByStatus(requests));
        response.setTasksByRole(countByRole(requests));
        response.setAverageLeadTimeHours(averageLeadTimeHours(requests));
        response.setAverageReturnTimeHours(averageReturnTimeHours(requests));
        return response;
    }

    private DemandeRequest load(String requestId) {
        return requestRepository.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Unknown request id: " + requestId));
    }

    private void ensureMutable(DemandeRequest request) {
        if (request.getStatus() != null && request.getStatus().isTerminal()) {
            throw new IllegalStateException("Request is already closed: " + request.getRequestId());
        }
    }

    private DemandeRequestResponse toResponse(DemandeRequest request) {
        DemandeRequestResponse response = new DemandeRequestResponse();
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
        response.setComments(request.getComments().stream().map(this::toCommentResponse).collect(Collectors.toList()));
        return response;
    }

    private DemandeCommentResponse toCommentResponse(DemandeComment comment) {
        return new DemandeCommentResponse(comment.getWorkflowStep(), comment.getActor(), comment.getComment(), comment.getCreatedAt());
    }

    private String normalizeWorkflowStep(String workflowStep) {
        String normalized = workflowStep.trim().toUpperCase();
        DemandeRequestStatus.fromValue(normalized);
        return normalized;
    }

    private void addWorkflowComment(DemandeRequest request, String text, Instant createdAt) {
        if (!StringUtils.hasText(text)) {
            return;
        }
        Instant eventTime = createdAt == null ? Instant.now() : createdAt;
        String workflowStep = request.getStatus() == null ? "UNKNOWN" : request.getStatus().name();
        String normalizedText = text.trim();
        request.addHistory(normalizedText);
        request.addComment(new DemandeComment(workflowStep, request.getCurrentActor(), normalizedText, eventTime));
    }

    private Map<String, Long> countByStatus(List<DemandeRequest> requests) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (DemandeRequestStatus status : DemandeRequestStatus.values()) {
            counts.put(status.name(), 0L);
        }
        for (DemandeRequest request : requests) {
            String key = request.getStatus() == null ? "UNKNOWN" : request.getStatus().name();
            counts.put(key, counts.getOrDefault(key, 0L) + 1L);
        }
        return counts;
    }

    private Map<String, Long> countByRole(List<DemandeRequest> requests) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (DemandeRequest request : requests) {
            String role = request.getCurrentActor() == null ? "UNASSIGNED" : request.getCurrentActor();
            counts.put(role, counts.getOrDefault(role, 0L) + 1L);
        }
        return counts;
    }

    private double averageLeadTimeHours(List<DemandeRequest> requests) {
        return requests.stream()
            .filter(request -> request.getCreatedAt() != null && request.getUpdatedAt() != null)
            .mapToLong(request -> Duration.between(request.getCreatedAt(), request.getUpdatedAt()).toMinutes())
            .average()
            .orElse(0.0) / 60.0;
    }

    private double averageReturnTimeHours(List<DemandeRequest> requests) {
        return requests.stream()
            .filter(request -> request.getHistory() != null && !request.getHistory().isEmpty())
            .mapToLong(request -> request.getHistory().size())
            .average()
            .orElse(0.0);
    }

    private String roleForStatus(DemandeRequestStatus status) {
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
