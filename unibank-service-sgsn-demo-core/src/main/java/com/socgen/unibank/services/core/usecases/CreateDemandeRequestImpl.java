package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestCreateRequest;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.usecases.CreateDemandeRequestUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDemandeRequestImpl implements CreateDemandeRequestUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeRequestResponse handle(DemandeRequestCreateRequest input, RequestContext context) {
        return workflowService.create(input);
    }
}
