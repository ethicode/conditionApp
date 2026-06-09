package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.model.DemandeRequestUpdateRequest;
import com.socgen.unibank.services.api.usecases.UpdateDemandeRequestUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDemandeRequestImpl implements UpdateDemandeRequestUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeRequestResponse handle(DemandeRequestUpdateRequest input, RequestContext context) {
        return workflowService.update(input);
    }
}
