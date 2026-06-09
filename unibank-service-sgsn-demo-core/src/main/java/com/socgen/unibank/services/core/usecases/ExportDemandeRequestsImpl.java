package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.api.usecases.ExportDemandeRequestsUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportDemandeRequestsImpl implements ExportDemandeRequestsUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public String export(DemandeRequestSearchCriteria input, RequestContext context) {
        return workflowService.export(input);
    }
}
