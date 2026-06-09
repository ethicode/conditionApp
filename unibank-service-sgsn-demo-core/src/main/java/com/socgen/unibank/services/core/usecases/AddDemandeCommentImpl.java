package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeCommentCreateRequest;
import com.socgen.unibank.services.api.model.DemandeCommentResponse;
import com.socgen.unibank.services.api.usecases.AddDemandeCommentUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddDemandeCommentImpl implements AddDemandeCommentUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public DemandeCommentResponse handle(String requestId, DemandeCommentCreateRequest input, RequestContext context) {
        return workflowService.addComment(requestId, input);
    }
}
