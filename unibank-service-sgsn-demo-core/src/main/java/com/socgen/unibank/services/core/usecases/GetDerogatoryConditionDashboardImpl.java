package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionDashboardResponse;
import com.socgen.unibank.services.api.usecases.GetDerogatoryConditionDashboardUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDerogatoryConditionDashboardImpl implements GetDerogatoryConditionDashboardUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public DerogatoryConditionDashboardResponse handle(RequestContext context) {
        return workflowService.dashboard();
    }
}
