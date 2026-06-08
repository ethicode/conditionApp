package com.socgen.unibank.services.gateway.inbound;
import com.socgen.unibank.platform.springboot.config.web.GraphQLController;
import com.socgen.unibank.services.api.UnibankServiceSgsnDemoAPI;
import org.springframework.web.bind.annotation.RestController;
@GraphQLController
@RestController
public interface UnibankServiceSgsnDemoEndpoint extends UnibankServiceSgsnDemoAPI {
}
