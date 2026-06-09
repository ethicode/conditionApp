package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.model.DemandeRequestUpdateRequest;

public interface UpdateDemandeRequestUseCase extends Command {

    DemandeRequestResponse handle(DemandeRequestUpdateRequest input, RequestContext context);
}