package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestValidationRequest;
import com.socgen.unibank.services.api.usecases.ValidateDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateDerogatoryConditionRequestImpl implements ValidateDerogatoryConditionRequestUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestValidationRequest input, RequestContext context) {
        return workflowService.validate(input);
    }
}
