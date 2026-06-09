package com.socgen.unibank.services.api.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequestSearchCriteria {
    private String requestId;
    private String customerReference;
    private String requestType;
    private String status;
    private String currentActor;
    private String keyword;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer page;
    private Integer size;
}
