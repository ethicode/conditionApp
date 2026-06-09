package com.socgen.unibank.services.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeSla {
    private String name;
    private DemandeRequestStatus startStatus;
    private DemandeRequestStatus endStatus;
    private boolean businessHoursOnly;
}
