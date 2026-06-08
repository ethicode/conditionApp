package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCloseRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;

public interface CloseDerogatoryConditionRequestUseCase extends Command {
    DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestCloseRequest input, RequestContext context);
}
