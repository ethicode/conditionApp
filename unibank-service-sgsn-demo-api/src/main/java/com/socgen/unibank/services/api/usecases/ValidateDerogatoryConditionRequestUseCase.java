package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestValidationRequest;

public interface ValidateDerogatoryConditionRequestUseCase extends Command {

    DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestValidationRequest input, RequestContext context);
}