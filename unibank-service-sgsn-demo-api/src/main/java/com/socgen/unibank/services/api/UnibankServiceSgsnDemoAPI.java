package com.socgen.unibank.services.api;

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
import com.socgen.unibank.services.api.usecases.AssignDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.AddDemandeCommentUseCase;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Hello world")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(name = "sgabs", produces = "application/json")
public interface UnibankServiceSgsnDemoAPI extends
    CreateSgabsHelloWorld,
    CreateDemandeRequestUseCase,
    UpdateDemandeRequestUseCase,
    ValidateDemandeRequestUseCase,
    ReturnDemandeRequestUseCase,
    AssignDemandeRequestUseCase,
    CloseDemandeRequestUseCase,
    AddDemandeCommentUseCase,
    ListDemandeCommentsUseCase,
    SearchDemandeRequestsUseCase,
    ExportDemandeRequestsUseCase,
    GetDemandeDashboardUseCase {

    @Operation(
        summary = "Create a new SgabsHelloWorld",
        parameters = {
            @Parameter(ref = "entityIdHeader", required = true)
        }
    )
    @PostMapping("/sgabs-hello-world")
    @Override
    SgabsHelloWorldResponse handle(@RequestBody SgabsHelloWorldRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestCreateRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PutMapping("/demandes")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestUpdateRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes/validate")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestValidationRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes/return")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestReturnRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes/assign")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestAssignmentRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes/close")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestCloseRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes/{requestId}/comments")
    @Override
    DemandeCommentResponse handle(@PathVariable("requestId") String requestId, @RequestBody DemandeCommentCreateRequest input, @Parameter(hidden = true) RequestContext ctx);

    @GetMapping("/demandes/{requestId}/comments")
    @Override
    List<DemandeCommentResponse> handle(@PathVariable("requestId") String requestId, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes/search")
    @Override
    DemandeRequestSearchResponse handle(@RequestBody DemandeRequestSearchCriteria input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/demandes/export")
    @Override
    String export(@RequestBody DemandeRequestSearchCriteria input, @Parameter(hidden = true) RequestContext ctx);

    @GetMapping("/demandes/dashboard")
    @Override
    DemandeDashboardResponse handle(@Parameter(hidden = true) RequestContext ctx);
}
