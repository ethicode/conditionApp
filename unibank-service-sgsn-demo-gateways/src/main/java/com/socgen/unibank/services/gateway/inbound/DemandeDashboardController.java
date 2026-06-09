package com.socgen.unibank.services.gateway.inbound;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.platform.springboot.config.web.GraphQLController;
import com.socgen.unibank.services.api.model.DemandeDashboardResponse;
import com.socgen.unibank.services.api.model.DemandeRequestSearchCriteria;
import com.socgen.unibank.services.api.usecases.ExportDemandeRequestsUseCase;
import com.socgen.unibank.services.api.usecases.GetDemandeDashboardUseCase;
import com.socgen.unibank.services.api.usecases.SearchDemandeRequestsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@GraphQLController
@RestController
@RequestMapping(path = "/demandes", produces = "application/json")
public interface DemandeDashboardController extends
    SearchDemandeRequestsUseCase,
    ExportDemandeRequestsUseCase,
    GetDemandeDashboardUseCase {

    @PostMapping("/search")
    @Override
    com.socgen.unibank.services.api.model.DemandeRequestSearchResponse handle(@RequestBody DemandeRequestSearchCriteria input, RequestContext context);

    @PostMapping("/export")
    @Override
    String export(@RequestBody DemandeRequestSearchCriteria input, RequestContext context);

    @GetMapping("/dashboard")
    @Override
    DemandeDashboardResponse handle(RequestContext context);
}
