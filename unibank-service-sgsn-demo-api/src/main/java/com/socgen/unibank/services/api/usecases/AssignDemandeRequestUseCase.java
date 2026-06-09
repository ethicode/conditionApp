package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DemandeRequestResponse;

public interface AssignDemandeRequestUseCase extends Command {
    DemandeRequestResponse handle(DemandeRequestAssignmentRequest input, RequestContext context);
}
