package com.incident.triage.service;

import com.incident.triage.model.Incident;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AITriageService {

    public Incident processIncidentWithAI(Incident incident) {
        // Simulated AI logic for severity classification
        String severity = determineSeverity(incident);
        String category = determineCategory(incident);
        String suggestedAction = generateSuggestedAction(incident, severity, category);

        incident.setAiSeverity(severity);
        incident.setAiCategory(category);
        incident.setAiSuggestedAction(suggestedAction);

        return incident;
    }

    private String determineSeverity(Incident incident) {
        String description = incident.getDescription().toLowerCase();
        String title = incident.getTitle().toLowerCase();

        // High severity keywords
        if (containsAny(description, title, "down", "outage", "critical", "emergency", "production")) {
            return "HIGH";
        }

        // Medium severity keywords
        if (containsAny(description, title, "slow", "performance", "timeout", "error")) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private String determineCategory(Incident incident) {
        String description = incident.getDescription().toLowerCase();
        String affectedService = incident.getAffectedService().toLowerCase();

        if (containsAny(description, affectedService, "database", "db", "sql")) {
            return "DATABASE";
        }
        if (containsAny(description, affectedService, "network", "connectivity", "internet")) {
            return "NETWORK";
        }
        if (containsAny(description, affectedService, "server", "cpu", "memory", "disk")) {
            return "INFRASTRUCTURE";
        }

        return "APPLICATION";
    }

    private String generateSuggestedAction(Incident incident, String severity, String category) {
        return switch (severity) {
            case "HIGH" -> "Immediate escalation to on-call engineer required";
            case "MEDIUM" -> "Assign to appropriate team within 2 hours";
            case "LOW" -> "Add to team backlog for next business day";
            default -> "Review and categorize manually";
        };
    }

    private boolean containsAny(String text1, String text2, String... keywords) {
        return Arrays.stream(keywords)
                .anyMatch(keyword -> text1.contains(keyword) || text2.contains(keyword));
    }
}
