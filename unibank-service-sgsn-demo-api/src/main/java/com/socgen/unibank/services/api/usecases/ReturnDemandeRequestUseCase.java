package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;
import com.socgen.unibank.services.api.model.DemandeRequestReturnRequest;

public interface ReturnDemandeRequestUseCase extends Command {
	DemandeRequestResponse handle(DemandeRequestReturnRequest input, RequestContext context);
}