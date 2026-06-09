package com.socgen.unibank.services.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeCommentCreateRequest {
    private String workflowStep;
    private String comment;
}
