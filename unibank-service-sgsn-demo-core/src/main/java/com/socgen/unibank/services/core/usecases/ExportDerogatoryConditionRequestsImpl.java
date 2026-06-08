package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;
import com.socgen.unibank.services.api.usecases.ExportDerogatoryConditionRequestsUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportDerogatoryConditionRequestsImpl implements ExportDerogatoryConditionRequestsUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public String export(DerogatoryConditionRequestSearchCriteria input, RequestContext context) {
        return workflowService.export(input);
    }
}
