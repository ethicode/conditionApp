package com.socgen.unibank.services.core.model;

public enum DemandeRequestStatus {
    NEW_REQUEST("Nouvelle demande", false),
    WAITING_FOR_CORRECTION("En attente correction", false),
    WAITING_RM_VALIDATION("En attente validation RM", false),
    WAITING_DCE_VALIDATION("En attente validation DCE", false),
    DECLARED("Declare", false),
    WAITING_FOR_INFORMATION("En attente d information", false),
    WAITING_SIOP_VALIDATION("En attente de validation SIOP", false),
    IN_VALIDATION("En cours de validation", false),
    VALIDATED("Valide", false),
    RESOLVED("Resolu", false),
    CLOSED("Cloture", true),
    IN_PROGRESS("En cours", false),
    APPROVED("Approuve", false);

    private final String label;
    private final boolean terminal;

    DemandeRequestStatus(String label, boolean terminal) {
        this.label = label;
        this.terminal = terminal;
    }

    public String getLabel() {
        return label;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public static DemandeRequestStatus fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or blank");
        }
        for (DemandeRequestStatus status : values()) {
            if (status.name().equalsIgnoreCase(value.trim()) || status.label.equalsIgnoreCase(value.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
