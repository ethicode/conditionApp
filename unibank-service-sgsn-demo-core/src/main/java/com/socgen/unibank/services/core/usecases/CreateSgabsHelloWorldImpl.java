package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.SgabsHelloWorldRequest;
import com.socgen.unibank.services.api.model.SgabsHelloWorldResponse;
import com.socgen.unibank.services.api.usecases.CreateSgabsHelloWorld;
import org.springframework.stereotype.Service;

@Service
public class CreateSgabsHelloWorldImpl implements CreateSgabsHelloWorld {

    @Override
    public SgabsHelloWorldResponse handle(SgabsHelloWorldRequest input, RequestContext context) {
        // Implémentation de la logique de création
        return new SgabsHelloWorldResponse("Hello, " + input.getMessage());
    }
}
