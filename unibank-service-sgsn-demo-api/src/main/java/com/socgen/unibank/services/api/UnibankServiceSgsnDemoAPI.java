package com.socgen.unibank.services.api;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Hello world")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(name = "sgabs", produces = "application/json")
public interface UnibankServiceSgsnDemoAPI extends
    CreateSgabsHelloWorld,
    CreateDerogatoryConditionRequestUseCase,
    UpdateDerogatoryConditionRequestUseCase,
    ValidateDerogatoryConditionRequestUseCase,
    ReturnDerogatoryConditionRequestUseCase,
    AssignDerogatoryConditionRequestUseCase,
    CloseDerogatoryConditionRequestUseCase,
    SearchDerogatoryConditionRequestsUseCase,
    ExportDerogatoryConditionRequestsUseCase,
    GetDerogatoryConditionDashboardUseCase {

    @Operation(
        summary = "Create a new SgabsHelloWorld",
        parameters = {
            @Parameter(ref = "entityIdHeader", required = true)
        }
    )
    @PostMapping("/sgabs-hello-world")
    @Override
    SgabsHelloWorldResponse handle(@RequestBody SgabsHelloWorldRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/derogatory-conditions")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestCreateRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PutMapping("/derogatory-conditions")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestUpdateRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/derogatory-conditions/validate")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestValidationRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/derogatory-conditions/return")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestReturnRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/derogatory-conditions/assign")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestAssignmentRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/derogatory-conditions/close")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestCloseRequest input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/derogatory-conditions/search")
    @Override
    DerogatoryConditionRequestSearchResponse handle(@RequestBody DerogatoryConditionRequestSearchCriteria input, @Parameter(hidden = true) RequestContext ctx);

    @PostMapping("/derogatory-conditions/export")
    @Override
    String export(@RequestBody DerogatoryConditionRequestSearchCriteria input, @Parameter(hidden = true) RequestContext ctx);

    @GetMapping("/derogatory-conditions/dashboard")
    @Override
    DerogatoryConditionDashboardResponse handle(@Parameter(hidden = true) RequestContext ctx);
}
