package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeDashboardResponse;

public interface GetDemandeDashboardUseCase extends Command {
    DemandeDashboardResponse handle(RequestContext context);
}
