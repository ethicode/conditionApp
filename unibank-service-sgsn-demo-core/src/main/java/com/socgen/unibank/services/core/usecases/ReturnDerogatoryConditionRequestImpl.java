package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestReturnRequest;
import com.socgen.unibank.services.api.usecases.ReturnDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnDerogatoryConditionRequestImpl implements ReturnDerogatoryConditionRequestUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestReturnRequest input, RequestContext context) {
        return workflowService.returnRequest(input);
    }
}
