package com.socgen.unibank.services.gateway.outbound.entities;

import com.socgen.unibank.services.core.model.DemandeComment;
import com.socgen.unibank.services.core.model.DemandeRequest;
import com.socgen.unibank.services.core.model.DemandeRequestStatus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DemandeRequestEntity {
    private String requestId;
    private String customerReference;
    private String requestType;
    private String message;
    private String comment;
    private String currentActor;
    private DemandeRequestStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private List<DemandeRequestAttachmentEntity> attachments = new ArrayList<>();
    private List<DemandeRequestHistoryEntity> history = new ArrayList<>();

    public static DemandeRequestEntity fromDomain(DemandeRequest request) {
        DemandeRequestEntity entity = new DemandeRequestEntity();
        entity.requestId = request.getRequestId();
        entity.customerReference = request.getCustomerReference();
        entity.requestType = request.getRequestType();
        entity.message = request.getMessage();
        entity.comment = request.getComment();
        entity.currentActor = request.getCurrentActor();
        entity.status = request.getStatus();
        entity.createdAt = request.getCreatedAt();
        entity.updatedAt = request.getUpdatedAt();
        if (request.getAttachmentNames() != null) {
            for (String name : request.getAttachmentNames()) {
                entity.attachments.add(new DemandeRequestAttachmentEntity(name, request.getCurrentActor(), request.getUpdatedAt()));
            }
        }
        if (request.getComments() != null && !request.getComments().isEmpty()) {
            for (DemandeComment demandeComment : request.getComments()) {
                entity.history.add(new DemandeRequestHistoryEntity(
                    demandeComment.getCreatedAt(),
                    demandeComment.getActor(),
                    demandeComment.getWorkflowStep(),
                    demandeComment.getWorkflowStep(),
                    demandeComment.getComment()
                ));
            }
        } else if (request.getHistory() != null) {
            for (String item : request.getHistory()) {
                entity.history.add(new DemandeRequestHistoryEntity(
                    request.getUpdatedAt(),
                    request.getCurrentActor(),
                    request.getStatus() == null ? null : request.getStatus().name(),
                    request.getStatus() == null ? null : request.getStatus().name(),
                    item
                ));
            }
        }
        return entity;
    }

    public DemandeRequest toDomain() {
        DemandeRequest request = new DemandeRequest();
        request.setRequestId(requestId);
        request.setCustomerReference(customerReference);
        request.setRequestType(requestType);
        request.setMessage(message);
        request.setComment(comment);
        request.setCurrentActor(currentActor);
        request.setStatus(status);
        request.setCreatedAt(createdAt);
        request.setUpdatedAt(updatedAt);
        if (attachments != null) {
            for (DemandeRequestAttachmentEntity attachment : attachments) {
                request.addAttachment(attachment.getFileName());
            }
        }
        if (history != null) {
            for (DemandeRequestHistoryEntity item : history) {
                request.addHistory(item.getComment());
                request.addComment(new DemandeComment(
                    item.getWorkflowStep() == null ? item.getStatus() : item.getWorkflowStep(),
                    item.getActor(),
                    item.getComment(),
                    item.getEventAt()
                ));
            }
        }
        return request;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCurrentActor() {
        return currentActor;
    }

    public void setCurrentActor(String currentActor) {
        this.currentActor = currentActor;
    }

    public DemandeRequestStatus getStatus() {
        return status;
    }

    public void setStatus(DemandeRequestStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<DemandeRequestAttachmentEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DemandeRequestAttachmentEntity> attachments) {
        this.attachments = attachments;
    }

    public List<DemandeRequestHistoryEntity> getHistory() {
        return history;
    }

    public void setHistory(List<DemandeRequestHistoryEntity> history) {
        this.history = history;
    }
}
