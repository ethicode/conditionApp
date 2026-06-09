package com.socgen.unibank.services.gateway.inbound;

import com.socgen.unibank.services.api.model.DemandeCommentCreateRequest;
import com.socgen.unibank.services.api.model.DemandeCommentResponse;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.platform.springboot.config.web.GraphQLController;
import com.socgen.unibank.services.api.model.DemandeRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DemandeRequestCloseRequest;
import com.socgen.unibank.services.api.model.DemandeRequestCreateRequest;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.model.DemandeRequestReturnRequest;
import com.socgen.unibank.services.api.model.DemandeRequestUpdateRequest;
import com.socgen.unibank.services.api.model.DemandeRequestValidationRequest;
import com.socgen.unibank.services.api.usecases.AddDemandeCommentUseCase;
import com.socgen.unibank.services.api.usecases.AssignDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.CloseDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.CreateDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.ListDemandeCommentsUseCase;
import com.socgen.unibank.services.api.usecases.ReturnDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.UpdateDemandeRequestUseCase;
import com.socgen.unibank.services.api.usecases.ValidateDemandeRequestUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@GraphQLController
@RestController
@RequestMapping(path = "/demandes", produces = "application/json")
public interface DemandeRequestController extends
    CreateDemandeRequestUseCase,
    UpdateDemandeRequestUseCase,
    ValidateDemandeRequestUseCase,
    ReturnDemandeRequestUseCase,
    AssignDemandeRequestUseCase,
    CloseDemandeRequestUseCase,
    AddDemandeCommentUseCase,
    ListDemandeCommentsUseCase {

    @PostMapping
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestCreateRequest input, RequestContext context);

    @PutMapping
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestUpdateRequest input, RequestContext context);

    @PostMapping("/validate")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestValidationRequest input, RequestContext context);

    @PostMapping("/return")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestReturnRequest input, RequestContext context);

    @PostMapping("/assign")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestAssignmentRequest input, RequestContext context);

    @PostMapping("/close")
    @Override
    DemandeRequestResponse handle(@RequestBody DemandeRequestCloseRequest input, RequestContext context);

    @PostMapping("/{requestId}/comments")
    @Override
    DemandeCommentResponse handle(@PathVariable("requestId") String requestId, @RequestBody DemandeCommentCreateRequest input, RequestContext context);

    @org.springframework.web.bind.annotation.GetMapping("/{requestId}/comments")
    @Override
    List<DemandeCommentResponse> handle(@PathVariable("requestId") String requestId, RequestContext context);
}
