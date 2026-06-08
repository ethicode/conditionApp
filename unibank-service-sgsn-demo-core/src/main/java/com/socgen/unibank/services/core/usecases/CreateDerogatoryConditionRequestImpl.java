package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCreateRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.usecases.CreateDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDerogatoryConditionRequestImpl implements CreateDerogatoryConditionRequestUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestCreateRequest input, RequestContext context) {
        return workflowService.create(input);
    }
}
