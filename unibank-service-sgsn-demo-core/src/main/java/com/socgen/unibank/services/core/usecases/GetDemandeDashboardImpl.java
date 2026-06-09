package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeDashboardResponse;
import com.socgen.unibank.services.api.usecases.GetDemandeDashboardUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDemandeDashboardImpl implements GetDemandeDashboardUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeDashboardResponse handle(RequestContext context) {
        return workflowService.dashboard();
    }
}
