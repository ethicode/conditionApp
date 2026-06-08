package com.socgen.unibank.services.gateway.outbound.entities;

import java.time.Instant;

public class DerogatoryConditionRequestAttachmentEntity {
    private String fileName;
    private String uploadedBy;
    private Instant uploadedAt;

    public DerogatoryConditionRequestAttachmentEntity() {
    }

    public DerogatoryConditionRequestAttachmentEntity(String fileName, String uploadedBy, Instant uploadedAt) {
        this.fileName = fileName;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = uploadedAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
