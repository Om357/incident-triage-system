package com.incident.triage.dto;

import jakarta.validation.constraints.NotBlank;

public class IncidentRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Affected service is required")
    private String affectedService;

    public IncidentRequest() {
        this.title = "";
        this.description = "";
        this.affectedService = "";
    }

    public IncidentRequest(String title, String description, String affectedService) {
        this.title = title;
        this.description = description;
        this.affectedService = affectedService;
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
}
