package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeCommentCreateRequest;
import com.socgen.unibank.services.api.model.DemandeCommentResponse;

public interface AddDemandeCommentUseCase extends Command {
    DemandeCommentResponse handle(String requestId, DemandeCommentCreateRequest input, RequestContext context);
}
