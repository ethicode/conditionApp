package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestCloseRequest;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;

public interface CloseDemandeRequestUseCase extends Command {
    DemandeRequestResponse handle(DemandeRequestCloseRequest input, RequestContext context);
}
