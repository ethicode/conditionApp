package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionDashboardResponse;

public interface GetDerogatoryConditionDashboardUseCase extends Command {
    DerogatoryConditionDashboardResponse handle(RequestContext context);
}
