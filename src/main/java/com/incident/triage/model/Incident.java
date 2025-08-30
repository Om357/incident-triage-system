package com.incident.triage.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String affectedService;

    // AI-generated fields
    private String aiSeverity; // HIGH, MEDIUM, LOW
    private String aiCategory; // NETWORK, DATABASE, APPLICATION, etc.
    private String aiSuggestedAction;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Incident() {
        this.title = "";
        this.description = "";
        this.affectedService = "";
        this.aiSeverity = null;          // will be set later by AI
        this.aiCategory = null;          // will be set later by AI
        this.aiSuggestedAction = null;   // will be set later by AI
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


    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", affectedService='" + affectedService + '\'' +
                ", aiSeverity='" + aiSeverity + '\'' +
                ", aiCategory='" + aiCategory + '\'' +
                ", aiSuggestedAction='" + aiSuggestedAction + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
