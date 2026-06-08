package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestUpdateRequest;

public interface UpdateDerogatoryConditionRequestUseCase extends Command {

    DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestUpdateRequest input, RequestContext context);
}