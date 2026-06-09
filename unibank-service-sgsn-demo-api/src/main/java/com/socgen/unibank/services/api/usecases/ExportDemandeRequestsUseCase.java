package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;

public interface ExportDemandeRequestsUseCase extends Command {
    String export(DemandeRequestSearchCriteria input, RequestContext context);
}
