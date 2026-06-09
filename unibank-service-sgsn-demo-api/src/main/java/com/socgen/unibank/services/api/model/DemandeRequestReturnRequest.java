package com.socgen.unibank.services.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequestReturnRequest {
    private String requestId;
    private String targetStatus;
    private String reason;
    private String comment;
}
