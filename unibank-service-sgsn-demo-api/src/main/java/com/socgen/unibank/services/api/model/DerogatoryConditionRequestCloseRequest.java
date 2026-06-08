package com.socgen.unibank.services.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerogatoryConditionRequestCloseRequest {
    private String requestId;
    private String comment;
    private boolean approved;
}
