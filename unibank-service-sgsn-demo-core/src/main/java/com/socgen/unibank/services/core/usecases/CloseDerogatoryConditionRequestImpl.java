package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestCloseRequest;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestResponse;
import com.socgen.unibank.services.api.usecases.CloseDerogatoryConditionRequestUseCase;
import com.socgen.unibank.services.core.services.DerogatoryConditionWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloseDerogatoryConditionRequestImpl implements CloseDerogatoryConditionRequestUseCase {

    private final DerogatoryConditionWorkflowService workflowService;

    @Override
    public DerogatoryConditionRequestResponse handle(DerogatoryConditionRequestCloseRequest input, RequestContext context) {
        return workflowService.close(input);
    }
}
