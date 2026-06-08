package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.usecases.AssignDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignDerogatoryConditionRequestImpl implements AssignDerogatoryConditionRequestUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestAssignmentRequest input, RequestContext context) {
        return workflowService.assign(input);
    }
}
