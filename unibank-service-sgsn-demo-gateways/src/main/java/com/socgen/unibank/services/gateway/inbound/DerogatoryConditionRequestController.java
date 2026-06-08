package com.socgen.unibank.services.gateway.inbound;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.platform.springboot.config.web.GraphQLController;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCloseRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCreateRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestReturnRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestUpdateRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestValidationRequest;
import com.socgen.unibank.services.api.usecases.AssignDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.CloseDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.CreateDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.ReturnDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.UpdateDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.api.usecases.ValidateDerogatoryConditionRequestUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@GraphQLController
@RestController
@RequestMapping(path = "/derogatory-conditions", produces = "application/json")
public interface DerogatoryConditionRequestController extends
    CreateDerogatoryConditionRequestUseCase,
    UpdateDerogatoryConditionRequestUseCase,
    ValidateDerogatoryConditionRequestUseCase,
    ReturnDerogatoryConditionRequestUseCase,
    AssignDerogatoryConditionRequestUseCase,
    CloseDerogatoryConditionRequestUseCase {

    @PostMapping
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestCreateRequest input, RequestContext context);

    @PutMapping
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestUpdateRequest input, RequestContext context);

    @PostMapping("/validate")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestValidationRequest input, RequestContext context);

    @PostMapping("/return")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestReturnRequest input, RequestContext context);

    @PostMapping("/assign")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestAssignmentRequest input, RequestContext context);

    @PostMapping("/close")
    @Override
    DerogatoryConditionRequestResponse handle(@RequestBody DerogatoryConditionRequestCloseRequest input, RequestContext context);
}
