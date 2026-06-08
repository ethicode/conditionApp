package com.socgen.unibank.services.core.model;

public enum DerogatoryConditionRequestTransition {
    CREATED(DerogatoryConditionRequestStatus.NEW_REQUEST, DerogatoryConditionRequestStatus.WAITING_RM_VALIDATION),
    CORRECTED(DerogatoryConditionRequestStatus.WAITING_FOR_CORRECTION, DerogatoryConditionRequestStatus.WAITING_RM_VALIDATION),
    RM_VALIDATED(DerogatoryConditionRequestStatus.WAITING_RM_VALIDATION, DerogatoryConditionRequestStatus.WAITING_DCE_VALIDATION),
    DCE_VALIDATED(DerogatoryConditionRequestStatus.WAITING_DCE_VALIDATION, DerogatoryConditionRequestStatus.DECLARED),
    SIOP_ASSIGNED(DerogatoryConditionRequestStatus.DECLARED, DerogatoryConditionRequestStatus.APPROVED),
    SIOP_SENT(DerogatoryConditionRequestStatus.APPROVED, DerogatoryConditionRequestStatus.WAITING_SIOP_VALIDATION),
    RUN_VALIDATED(DerogatoryConditionRequestStatus.WAITING_SIOP_VALIDATION, DerogatoryConditionRequestStatus.VALIDATED),
    FINALIZED(DerogatoryConditionRequestStatus.VALIDATED, DerogatoryConditionRequestStatus.RESOLVED),
    METIER_CLOSED(DerogatoryConditionRequestStatus.RESOLVED, DerogatoryConditionRequestStatus.CLOSED),
    RETURNED(DerogatoryConditionRequestStatus.VALIDATED, DerogatoryConditionRequestStatus.IN_PROGRESS);

    private final DerogatoryConditionRequestStatus from;
    private final DerogatoryConditionRequestStatus to;

    DerogatoryConditionRequestTransition(DerogatoryConditionRequestStatus from, DerogatoryConditionRequestStatus to) {
        this.from = from;
        this.to = to;
    }

    public DerogatoryConditionRequestStatus getFrom() {
        return from;
    }

    public DerogatoryConditionRequestStatus getTo() {
        return to;
    }
}
