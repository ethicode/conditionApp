package com.socgen.unibank.services.api.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerogatoryConditionRequestSearchResponse {
    private long totalCount;
    private List<DerogatoryConditionRequestResponse> items = new ArrayList<>();
}
