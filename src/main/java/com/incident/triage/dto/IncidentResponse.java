package com.incident.triage.dto;

import java.time.LocalDateTime;

public class IncidentResponse {

        private Long id;
        private String title;
        private String description;
        private String affectedService;
        private String aiSeverity;
        private String aiCategory;
        private String aiSuggestedAction;
        private LocalDateTime createdAt;



    public IncidentResponse() {
        this.id = null;
        this.title = "";
        this.description = "";
        this.affectedService = "";
        this.aiSeverity = null;          // can be populated later
        this.aiCategory = null;          // can be populated later
        this.aiSuggestedAction = null;   // can be populated later
        this.createdAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAffectedService() {
        return affectedService;
    }

    public void setAffectedService(String affectedService) {
        this.affectedService = affectedService;
    }

    public String getAiSeverity() {
        return aiSeverity;
    }

    public void setAiSeverity(String aiSeverity) {
        this.aiSeverity = aiSeverity;
    }

    public String getAiCategory() {
        return aiCategory;
    }

    public void setAiCategory(String aiCategory) {
        this.aiCategory = aiCategory;
    }

    public String getAiSuggestedAction() {
        return aiSuggestedAction;
    }

    public void setAiSuggestedAction(String aiSuggestedAction) {
        this.aiSuggestedAction = aiSuggestedAction;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
