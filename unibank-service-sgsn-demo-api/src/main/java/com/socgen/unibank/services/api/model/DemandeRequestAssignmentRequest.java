package com.socgen.unibank.services.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequestAssignmentRequest {
    private String requestId;
    private String assignee;
    private String comment;
}
