package com.socgen.unibank.services.api.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeCommentResponse {
    private String workflowStep;
    private String actor;
    private String comment;
    private Instant createdAt;
}
