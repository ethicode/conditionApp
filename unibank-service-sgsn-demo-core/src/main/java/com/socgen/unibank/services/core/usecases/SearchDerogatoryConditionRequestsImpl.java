package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchResponse;
import com.socgen.unibank.services.api.usecases.SearchDerogatoryConditionRequestsUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchDerogatoryConditionRequestsImpl implements SearchDerogatoryConditionRequestsUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public DerogatoryConditionRequestSearchResponse handle(DerogatoryConditionRequestSearchCriteria input, RequestContext context) {
        return workflowService.search(input);
    }
}
