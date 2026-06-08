package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;

public interface ExportDerogatoryConditionRequestsUseCase extends Command {
    String export(DerogatoryConditionRequestSearchCriteria input, RequestContext context);
}
