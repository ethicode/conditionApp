package com.socgen.unibank.services.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerogatoryConditionSla {
    private String name;
    private DerogatoryConditionRequestStatus startStatus;
    private DerogatoryConditionRequestStatus endStatus;
    private boolean businessHoursOnly;
}
