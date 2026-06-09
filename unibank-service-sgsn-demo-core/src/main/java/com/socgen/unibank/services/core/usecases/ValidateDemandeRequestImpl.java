package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.model.DemandeRequestValidationRequest;
import com.socgen.unibank.services.api.usecases.ValidateDemandeRequestUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateDemandeRequestImpl implements ValidateDemandeRequestUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeRequestResponse handle(DemandeRequestValidationRequest input, RequestContext context) {
        return workflowService.validate(input);
    }
}
