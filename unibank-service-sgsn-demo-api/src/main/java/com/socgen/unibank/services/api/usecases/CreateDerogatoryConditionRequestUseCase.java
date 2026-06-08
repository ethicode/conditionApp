package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCreateRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;

public interface CreateDerogatoryConditionRequestUseCase extends Command {
    DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestCreateRequest input, RequestContext context);
}