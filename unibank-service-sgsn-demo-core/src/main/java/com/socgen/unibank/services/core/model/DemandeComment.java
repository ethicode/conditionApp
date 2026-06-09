package com.socgen.unibank.services.core.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeComment {
    private String workflowStep;
    private String actor;
    private String comment;
    private Instant createdAt;
}
