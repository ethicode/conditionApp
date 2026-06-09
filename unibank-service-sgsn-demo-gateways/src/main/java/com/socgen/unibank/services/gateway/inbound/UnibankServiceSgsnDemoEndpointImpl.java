package com.socgen.unibank.services.gateway.inbound;

import com.socgen.unibank.platform.models.RequestContext;
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
import com.socgen.unibank.services.api.model.SgabsHelloWorldRequest;
import com.socgen.unibank.services.api.model.SgabsHelloWorldResponse;
import com.socgen.unibank.services.api.usecases.AddDemandeCommentUseCase;
import com.socgen.unibank.services.api.usecases.AssignDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.CloseDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.CreateDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.CreateSgabsHelloWorld;
import com.socgen.unibank.services.api.usecases.ExportDemandeRequestsUseCase;
import com.socgen.unibank.services.api.usecases.GetDemandeDashboardUseCase;
import com.socgen.unibank.services.api.usecases.ListDemandeCommentsUseCase;
import com.socgen.unibank.services.api.usecases.ReturnDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.SearchDemandeRequestsUseCase;
import com.socgen.unibank.services.api.usecases.UpdateDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.ValidateDemandeRequestUseCase;
import com.socgen.unibank.services.core.gateways.outbound.DemandeRequestRepositoryGateway;
import com.socgen.unibank.services.core.model.DemandeRequest;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class UnibankServiceSgsnDemoEndpointImpl implements UnibankServiceSgsnDemoEndpoint {

    private final CreateSgabsHelloWorld createSgabsHelloWorld;
    private final CreateDemandeRequestUseCase createDemandeRequestUseCase;
    private final UpdateDemandeRequestUseCase updateDemandeRequestUseCase;
    private final ValidateDemandeRequestUseCase validateDemandeRequestUseCase;
    private final ReturnDemandeRequestUseCase returnDemandeRequestUseCase;
    private final AssignDemandeRequestUseCase assignDemandeRequestUseCase;
    private final CloseDemandeRequestUseCase closeDemandeRequestUseCase;
    private final AddDemandeCommentUseCase addDemandeCommentUseCase;
    private final ListDemandeCommentsUseCase listDemandeCommentsUseCase;
    private final SearchDemandeRequestsUseCase searchDemandeRequestsUseCase;
    private final ExportDemandeRequestsUseCase exportDemandeRequestsUseCase;
    private final GetDemandeDashboardUseCase getDemandeDashboardUseCase;
    private final DemandeRequestRepositoryGateway requestRepository;

    @Override
    public SgabsHelloWorldResponse handle(SgabsHelloWorldRequest input, RequestContext ctx) {
        return createSgabsHelloWorld.handle(input, ctx);
    }

    @Override
    public DemandeRequestResponse handle(DemandeRequestCreateRequest input, RequestContext ctx) {
        return createDemandeRequestUseCase.handle(input, ctx);
    }

    @Override
    public DemandeRequestResponse handle(DemandeRequestUpdateRequest input, RequestContext ctx) {
        return updateDemandeRequestUseCase.handle(input, ctx);
    }

    @Override
    public DemandeRequestResponse handle(DemandeRequestValidationRequest input, RequestContext ctx) {
        return validateDemandeRequestUseCase.handle(input, ctx);
    }

    @Override
    public DemandeRequestResponse handle(DemandeRequestReturnRequest input, RequestContext ctx) {
        return returnDemandeRequestUseCase.handle(input, ctx);
    }

    @Override
    public DemandeRequestResponse handle(DemandeRequestAssignmentRequest input, RequestContext ctx) {
        return assignDemandeRequestUseCase.handle(input, ctx);
    }

    @Override
    public DemandeRequestResponse handle(DemandeRequestCloseRequest input, RequestContext ctx) {
        return closeDemandeRequestUseCase.handle(input, ctx);
    }

    @Override
    public DemandeCommentResponse handle(String requestId, DemandeCommentCreateRequest input, RequestContext ctx) {
        return addDemandeCommentUseCase.handle(requestId, input, ctx);
    }

    @Override
    public List<DemandeCommentResponse> handle(String requestId, RequestContext ctx) {
        return listDemandeCommentsUseCase.handle(requestId, ctx);
    }

    @Override
    public DemandeRequestSearchResponse handle(DemandeRequestSearchCriteria input, RequestContext ctx) {
        return searchDemandeRequestsUseCase.handle(input, ctx);
    }

    @GetMapping(path = "/demandes", produces = "application/json")
    public DemandeRequestSearchResponse list(
        @RequestParam(name = "requestId", required = false) String requestId,
        @RequestParam(name = "customerReference", required = false) String customerReference,
        @RequestParam(name = "requestType", required = false) String requestType,
        @RequestParam(name = "status", required = false) String status,
        @RequestParam(name = "currentActor", required = false) String currentActor,
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
        @RequestParam(name = "page", required = false) Integer page,
        @RequestParam(name = "size", required = false) Integer size
    ) {
        DemandeRequestSearchCriteria criteria = new DemandeRequestSearchCriteria(
            requestId,
            customerReference,
            requestType,
            status,
            currentActor,
            keyword,
            fromDate,
            toDate,
            page,
            size
        );
        return searchDemandeRequestsUseCase.handle(criteria, new RequestContext());
    }

    @GetMapping(path = "/demandes/{requestId}", produces = "application/json")
    public DemandeRequestResponse getById(@PathVariable String requestId) {
        DemandeRequest request = requestRepository.findById(requestId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown request id: " + requestId));
        return toResponse(request);
    }

    @DeleteMapping(path = "/demandes/{requestId}")
    public void deleteById(@PathVariable String requestId) {
        boolean deleted = requestRepository.deleteById(requestId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown request id: " + requestId);
        }
    }

    @Override
    public String export(DemandeRequestSearchCriteria input, RequestContext ctx) {
        return exportDemandeRequestsUseCase.export(input, ctx);
    }

    @Override
    public DemandeDashboardResponse handle(RequestContext ctx) {
        return getDemandeDashboardUseCase.handle(ctx);
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
        response.setAttachmentNames(request.getAttachmentNames());
        response.setHistory(request.getHistory());
        response.setComments(request.getComments().stream()
            .map(item -> new DemandeCommentResponse(item.getWorkflowStep(), item.getActor(), item.getComment(), item.getCreatedAt()))
            .toList());
        return response;
    }
}
