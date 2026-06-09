package com.socgen.unibank.services.api.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequestResponse {
    private String requestId;
    private String customerReference;
    private String requestType;
    private String status;
    private String currentActor;
    private String message;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
    private List<String> attachmentNames = new ArrayList<>();
    private List<String> history = new ArrayList<>();
    private List<DemandeCommentResponse> comments = new ArrayList<>();
}
