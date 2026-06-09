package com.socgen.unibank.services.api.model;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeDashboardResponse {
    private long totalRequests;
    private long resolvedRequests;
    private long closedRequests;
    private double averageLeadTimeHours;
    private double averageReturnTimeHours;
    private Map<String, Long> requestsByStatus = new LinkedHashMap<>();
    private Map<String, Long> tasksByRole = new LinkedHashMap<>();
}
