package com.socgen.unibank.services.core.model;

public enum DemandeRequestTransition {
    CREATED(DemandeRequestStatus.NEW_REQUEST, DemandeRequestStatus.WAITING_RM_VALIDATION),
    CORRECTED(DemandeRequestStatus.WAITING_FOR_CORRECTION, DemandeRequestStatus.WAITING_RM_VALIDATION),
    RM_VALIDATED(DemandeRequestStatus.WAITING_RM_VALIDATION, DemandeRequestStatus.WAITING_DCE_VALIDATION),
    DCE_VALIDATED(DemandeRequestStatus.WAITING_DCE_VALIDATION, DemandeRequestStatus.DECLARED),
    SIOP_ASSIGNED(DemandeRequestStatus.DECLARED, DemandeRequestStatus.APPROVED),
    SIOP_SENT(DemandeRequestStatus.APPROVED, DemandeRequestStatus.WAITING_SIOP_VALIDATION),
    RUN_VALIDATED(DemandeRequestStatus.WAITING_SIOP_VALIDATION, DemandeRequestStatus.VALIDATED),
    FINALIZED(DemandeRequestStatus.VALIDATED, DemandeRequestStatus.RESOLVED),
    METIER_CLOSED(DemandeRequestStatus.RESOLVED, DemandeRequestStatus.CLOSED),
    RETURNED(DemandeRequestStatus.VALIDATED, DemandeRequestStatus.IN_PROGRESS);

    private final DemandeRequestStatus from;
    private final DemandeRequestStatus to;

    DemandeRequestTransition(DemandeRequestStatus from, DemandeRequestStatus to) {
        this.from = from;
        this.to = to;
    }

    public DemandeRequestStatus getFrom() {
        return from;
    }

    public DemandeRequestStatus getTo() {
        return to;
    }
}
