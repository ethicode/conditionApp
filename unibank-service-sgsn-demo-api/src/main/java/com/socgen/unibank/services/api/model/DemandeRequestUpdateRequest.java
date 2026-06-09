package com.socgen.unibank.services.api.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequestUpdateRequest {
    private String requestId;
    private String message;
    private String comment;
    private List<String> attachmentNames = new ArrayList<>();
}
