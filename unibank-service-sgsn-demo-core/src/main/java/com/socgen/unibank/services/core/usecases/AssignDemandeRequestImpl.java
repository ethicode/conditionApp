package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.usecases.AssignDemandeRequestUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignDemandeRequestImpl implements AssignDemandeRequestUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeRequestResponse handle(DemandeRequestAssignmentRequest input, RequestContext context) {
        return workflowService.assign(input);
    }
}
