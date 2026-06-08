package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.services.api.model.SgabsHelloWorldRequest;
import com.socgen.unibank.services.api.model.SgabsHelloWorldResponse;

public interface CreateSgabsHelloWorld extends Command {
    SgabsHelloWorldResponse handle(SgabsHelloWorldRequest input, RequestContext context);
}
