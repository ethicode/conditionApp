package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.api.model.DemandeRequestSearchResponse;

public interface SearchDemandeRequestsUseCase extends Command {
    DemandeRequestSearchResponse handle(DemandeRequestSearchCriteria input, RequestContext context);
}
