package com.socgen.unibank.services.core.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRequest {
    private String requestId;
    private String customerReference;
    private String requestType;
    private String message;
    private String comment;
    private String currentActor;
    private DemandeRequestStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private List<String> attachmentNames = new ArrayList<>();
    private List<String> history = new ArrayList<>();
    private List<DemandeComment> comments = new ArrayList<>();

    public void addHistory(String entry) {
        if (entry != null && !entry.isBlank()) {
            history.add(entry);
        }
    }

    public void addAttachment(String attachmentName) {
        if (attachmentName != null && !attachmentName.isBlank()) {
            attachmentNames.add(attachmentName);
        }
    }

    public void addComment(DemandeComment demandeComment) {
        if (demandeComment != null && demandeComment.getComment() != null && !demandeComment.getComment().isBlank()) {
            comments.add(demandeComment);
        }
    }

    public void touch() {
        updatedAt = Instant.now();
    }
}
