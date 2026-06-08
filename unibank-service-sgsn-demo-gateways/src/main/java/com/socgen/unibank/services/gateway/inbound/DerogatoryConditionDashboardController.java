package com.socgen.unibank.services.gateway.inbound;

import com.socgen.unibank.platform.models.RequestContext;
import com.socgen.unibank.platform.springboot.config.web.GraphQLController;
import com.socgen.unibank.services.api.model.DerogatoryConditionDashboardResponse;
import com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchCriteria;
import com.socgen.unibank.services.api.usecases.ExportDerogatoryConditionRequestsUseCase;
import com.socgen.unibank.services.api.usecases.GetDerogatoryConditionDashboardUseCase;
import com.socgen.unibank.services.api.usecases.SearchDerogatoryConditionRequestsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@GraphQLController
@RestController
@RequestMapping(path = "/derogatory-conditions", produces = "application/json")
public interface DerogatoryConditionDashboardController extends
    SearchDerogatoryConditionRequestsUseCase,
    ExportDerogatoryConditionRequestsUseCase,
    GetDerogatoryConditionDashboardUseCase {

    @PostMapping("/search")
    @Override
    com.socgen.unibank.services.api.model.DerogatoryConditionRequestSearchResponse handle(@RequestBody DerogatoryConditionRequestSearchCriteria input, RequestContext context);

    @PostMapping("/export")
    @Override
    String export(@RequestBody DerogatoryConditionRequestSearchCriteria input, RequestContext context);

    @GetMapping("/dashboard")
    @Override
    DerogatoryConditionDashboardResponse handle(RequestContext context);
}
