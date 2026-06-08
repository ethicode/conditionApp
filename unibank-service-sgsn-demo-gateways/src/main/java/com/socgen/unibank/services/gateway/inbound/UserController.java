package com.socgen.unibank.services.gateway.inbound;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user")
    public Map<String, String> me(Principal principal) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("username", principal == null ? "anonymous" : principal.getName());
        return response;
    }
}
