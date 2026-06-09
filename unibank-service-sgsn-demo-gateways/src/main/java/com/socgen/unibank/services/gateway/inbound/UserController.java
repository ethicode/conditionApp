package com.socgen.unibank.services.gateway.inbound;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final Map<String, UserDto> users = new ConcurrentHashMap<>();

    @PostConstruct
    void initUsers() {
        saveInternal(new UserDto("u-ace-001", "ace.user", "ACE User", "ace.user@demo.local", "ACE", true));
        saveInternal(new UserDto("u-siop-001", "siop.agent", "SIOP Agent", "siop.agent@demo.local", "AGENT_SIOP", true));
        saveInternal(new UserDto("u-admin-001", "platform.admin", "Platform Admin", "admin@demo.local", "ADMIN", true));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user")
    public UserDto me(Principal principal) {
        String username = principal == null ? "anonymous" : principal.getName();
        return users.values().stream()
            .filter(user -> username.equalsIgnoreCase(user.username()))
            .findFirst()
            .orElse(new UserDto("anonymous", username, "Anonymous", "", "VIEWER", true));
    }

    @GetMapping
    @Operation(summary = "List users")
    public List<UserDto> listUsers() {
        return users.values().stream()
            .sorted((left, right) -> left.userId().compareToIgnoreCase(right.userId()))
            .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id")
    public UserDto getUser(@PathVariable String userId) {
        UserDto user = users.get(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown user id: " + userId);
        }
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create user")
    public UserDto createUser(@RequestBody UserDto input) {
        if (input == null || isBlank(input.userId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }
        if (users.containsKey(input.userId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists: " + input.userId());
        }
        validateRole(input.role());
        return saveInternal(input);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user")
    public UserDto updateUser(@PathVariable String userId, @RequestBody UserDto input) {
        if (!users.containsKey(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown user id: " + userId);
        }
        if (input == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }
        String role = isBlank(input.role()) ? users.get(userId).role() : input.role();
        validateRole(role);
        UserDto merged = new UserDto(
            userId,
            isBlank(input.username()) ? users.get(userId).username() : input.username(),
            isBlank(input.displayName()) ? users.get(userId).displayName() : input.displayName(),
            isBlank(input.email()) ? users.get(userId).email() : input.email(),
            role,
            input.active()
        );
        return saveInternal(merged);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user")
    public void deleteUser(@PathVariable String userId) {
        if (users.remove(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown user id: " + userId);
        }
    }

    @GetMapping("/roles")
    @Operation(summary = "List supported roles")
    public List<Map<String, String>> listRoles() {
        List<Map<String, String>> roles = new ArrayList<>();
        for (String role : SUPPORTED_ROLES) {
            Map<String, String> line = new LinkedHashMap<>();
            line.put("role", role);
            roles.add(line);
        }
        return roles;
    }

    private static final List<String> SUPPORTED_ROLES = List.of(
        "ACE",
        "CHARGE_CLIENTELE",
        "RESPONSABLE_MARCHE",
        "AGENT_SIOP",
        "VALIDATEUR_SIOP",
        "METIER_ACE",
        "ADMIN",
        "VIEWER"
    );

    private UserDto saveInternal(UserDto input) {
        UserDto sanitized = new UserDto(
            input.userId(),
            blankToDefault(input.username(), input.userId()),
            blankToDefault(input.displayName(), input.userId()),
            blankToDefault(input.email(), input.userId() + "@demo.local"),
            blankToDefault(input.role(), "VIEWER"),
            input.active()
        );
        users.put(sanitized.userId(), sanitized);
        return sanitized;
    }

    private void validateRole(String role) {
        if (isBlank(role) || !SUPPORTED_ROLES.contains(role)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported role: " + role);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String blankToDefault(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    public record UserDto(
        String userId,
        String username,
        String displayName,
        String email,
        String role,
        boolean active
    ) {}
}
