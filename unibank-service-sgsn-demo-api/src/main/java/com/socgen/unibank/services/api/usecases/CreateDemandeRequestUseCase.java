package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestCreateRequest;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;

public interface CreateDemandeRequestUseCase extends Command {
    DemandeRequestResponse handle(DemandeRequestCreateRequest input, RequestContext context);
}