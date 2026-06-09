package com.socgen.unibank.services.core.usecases;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeCommentResponse;
import com.socgen.unibank.services.api.usecases.ListDemandeCommentsUseCase;
import com.socgen.unibank.services.core.services.DemandeWorkflowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListDemandeCommentsImpl implements ListDemandeCommentsUseCase {

    private final DemandeWorkflowService workflowService;

    @Override
    public List<DemandeCommentResponse> handle(String requestId, RequestContext context) {
        return workflowService.listComments(requestId);
    }
}
