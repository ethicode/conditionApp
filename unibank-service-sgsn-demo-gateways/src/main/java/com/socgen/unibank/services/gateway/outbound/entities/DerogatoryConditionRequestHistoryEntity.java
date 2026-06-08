package com.socgen.unibank.services.gateway.outbound.entities;

import java.time.Instant;

public class DerogatoryConditionRequestHistoryEntity {
    private Instant eventAt;
    private String actor;
    private String status;
    private String comment;

    public DerogatoryConditionRequestHistoryEntity() {
    }

    public DerogatoryConditionRequestHistoryEntity(Instant eventAt, String actor, String status, String comment) {
        this.eventAt = eventAt;
        this.actor = actor;
        this.status = status;
        this.comment = comment;
    }

    public Instant getEventAt() {
        return eventAt;
    }

    public void setEventAt(Instant eventAt) {
        this.eventAt = eventAt;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
