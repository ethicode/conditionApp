package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.api.model.DemandeRequestSearchResponse;
import com.socgen.unibank.services.api.usecases.SearchDemandeRequestsUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchDemandeRequestsImpl implements SearchDemandeRequestsUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeRequestSearchResponse handle(DemandeRequestSearchCriteria input, RequestContext context) {
        return workflowService.search(input);
    }
}
