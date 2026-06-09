package com.socgen.unibank.services.api.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequestSearchResponse {
    private long totalCount;
    private List<DemandeRequestResponse> items = new ArrayList<>();
}
