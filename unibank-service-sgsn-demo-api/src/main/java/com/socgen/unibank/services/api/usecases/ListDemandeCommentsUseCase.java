package com.socgen.unibank.services.api.usecases;

import com.socgen.unibank.platform.domain.Command;
import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.services.api.model.DemandeCommentResponse;
import java.util.List;

public interface ListDemandeCommentsUseCase extends Command {
    List<DemandeCommentResponse> handle(String requestId, RequestContext context);
}
