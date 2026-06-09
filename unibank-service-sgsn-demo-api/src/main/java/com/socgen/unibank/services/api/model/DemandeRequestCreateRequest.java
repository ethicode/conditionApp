package com.socgen.unibank.services.api.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequestCreateRequest {
    private String customerReference;
    private String requestType;
    private String message;
    private List<String> attachmentNames = new ArrayList<>();
}
