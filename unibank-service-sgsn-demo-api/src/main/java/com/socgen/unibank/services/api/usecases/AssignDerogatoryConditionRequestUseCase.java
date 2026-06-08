package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestAssignmentRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;

public interface AssignDerogatoryConditionRequestUseCase extends Command {
    DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestAssignmentRequest input, RequestContext context);
}
