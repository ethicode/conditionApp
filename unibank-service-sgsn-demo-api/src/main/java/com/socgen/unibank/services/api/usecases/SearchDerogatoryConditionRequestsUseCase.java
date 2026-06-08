package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchResponse;

public interface SearchDerogatoryConditionRequestsUseCase extends Command {
    DerogatoryConditionRequestSearchResponse handle(DerogatoryConditionRequestSearchCriteria input, RequestContext context);
}
