package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestReturnRequest;

public interface ReturnDerogatoryConditionRequestUseCase extends Command {
	DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestReturnRequest input, RequestContext context);
}