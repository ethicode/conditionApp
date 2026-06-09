package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.model.DemandeRequestReturnRequest;
import com.socgen.unibank.services.api.usecases.ReturnDemandeRequestUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnDemandeRequestImpl implements ReturnDemandeRequestUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeRequestResponse handle(DemandeRequestReturnRequest input, RequestContext context) {
        return workflowService.returnRequest(input);
    }
}
