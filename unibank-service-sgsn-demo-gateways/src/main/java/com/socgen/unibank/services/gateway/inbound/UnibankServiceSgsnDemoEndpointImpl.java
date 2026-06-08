package com.socgen.unibank.services.gateway.inbound;

import com.socgen.unibank.platform.models.RequestContext;
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
import com.socgen.unibank.services.api.model.SgabsHelloWorldRequest;
import com.socgen.unibank.services.api.model.SgabsHelloWorldResponse;
import com.socgen.unibank.services.api.usecases.AssignDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.CloseDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.CreateDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.CreateSgabsHelloWorld;
import com.socgen.unibank.services.api.usecases.ExportDerogatoryConditionRequestsUseCase;
import com.socgen.unibank.services.api.usecases.GetDerogatoryConditionDashboardUseCase;
import com.socgen.unibank.services.api.usecases.ReturnDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.SearchDerogatoryConditionRequestsUseCase;
import com.socgen.unibank.services.api.usecases.UpdateDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.ValidateDerogatoryConditionRequestUseCase;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UnibankServiceSgsnDemoEndpointImpl implements UnibankServiceSgsnDemoEndpoint {

    private final CreateSgabsHelloWorld createSgabsHelloWorld;
    private final CreateDerogatoryConditionRequestUseCase createDerogatoryConditionRequestUseCase;
    private final UpdateDerogatoryConditionRequestUseCase updateDerogatoryConditionRequestUseCase;
    private final ValidateDerogatoryConditionRequestUseCase validateDerogatoryConditionRequestUseCase;
    private final ReturnDerogatoryConditionRequestUseCase returnDerogatoryConditionRequestUseCase;
    private final AssignDerogatoryConditionRequestUseCase assignDerogatoryConditionRequestUseCase;
    private final CloseDerogatoryConditionRequestUseCase closeDerogatoryConditionRequestUseCase;
    private final SearchDerogatoryConditionRequestsUseCase searchDerogatoryConditionRequestsUseCase;
    private final ExportDerogatoryConditionRequestsUseCase exportDerogatoryConditionRequestsUseCase;
    private final GetDerogatoryConditionDashboardUseCase getDerogatoryConditionDashboardUseCase;

    @Override
    public SgabsHelloWorldResponse handle(SgabsHelloWorldRequest input, RequestContext ctx) {
        return createSgabsHelloWorld.handle(input, ctx);
    }

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestCreateRequest input, RequestContext ctx) {
        return createDerogatoryConditionRequestUseCase.handle(input, ctx);
    }

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestUpdateRequest input, RequestContext ctx) {
        return updateDerogatoryConditionRequestUseCase.handle(input, ctx);
    }

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestValidationRequest input, RequestContext ctx) {
        return validateDerogatoryConditionRequestUseCase.handle(input, ctx);
    }

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestReturnRequest input, RequestContext ctx) {
        return returnDerogatoryConditionRequestUseCase.handle(input, ctx);
    }

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestAssignmentRequest input, RequestContext ctx) {
        return assignDerogatoryConditionRequestUseCase.handle(input, ctx);
    }

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestCloseRequest input, RequestContext ctx) {
        return closeDerogatoryConditionRequestUseCase.handle(input, ctx);
    }

    @Override
    public DerogatoryConditionRequestSearchResponse handle(DerogatoryConditionRequestSearchCriteria input, RequestContext ctx) {
        return searchDerogatoryConditionRequestsUseCase.handle(input, ctx);
    }

    @GetMapping(path = "/derogatory-conditions", produces = "application/json")
    public DerogatoryConditionRequestSearchResponse list(
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
        DerogatoryConditionRequestSearchCriteria criteria = new DerogatoryConditionRequestSearchCriteria(
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
        return searchDerogatoryConditionRequestsUseCase.handle(criteria, new RequestContext());
    }

    @Override
    public String export(DerogatoryConditionRequestSearchCriteria input, RequestContext ctx) {
        return exportDerogatoryConditionRequestsUseCase.export(input, ctx);
    }

    @Override
    public DerogatoryConditionDashboardResponse handle(RequestContext ctx) {
        return getDerogatoryConditionDashboardUseCase.handle(ctx);
    }
}
