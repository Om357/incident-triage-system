package com.incident.triage.service;

import com.incident.triage.dto.IncidentRequest;
import com.incident.triage.dto.IncidentResponse;
import com.incident.triage.model.Incident;
import com.incident.triage.repository.IncidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final AITriageService aiTriageService;

    public IncidentService(IncidentRepository incidentRepository, AITriageService aiTriageService) {
        this.incidentRepository = incidentRepository;
        this.aiTriageService = aiTriageService;
    }

    public IncidentResponse createIncident(IncidentRequest request) {
        // Create incident entity
        Incident incident = new Incident();
        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setAffectedService(request.getAffectedService());
        incident.setCreatedAt(LocalDateTime.now());

        // Process with AI logic
        incident = aiTriageService.processIncidentWithAI(incident);

        // Save to database
        incident = incidentRepository.save(incident);

        // Convert to response DTO
        return convertToResponse(incident);
    }

    public List<IncidentResponse> getAllIncidents() {
        return incidentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public Optional<IncidentResponse> getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .map(this::convertToResponse);
    }

    private IncidentResponse convertToResponse(Incident incident) {
        IncidentResponse response = new IncidentResponse();
        response.setId(incident.getId());
        response.setTitle(incident.getTitle());
        response.setDescription(incident.getDescription());
        response.setAffectedService(incident.getAffectedService());
        response.setAiSeverity(incident.getAiSeverity());
        response.setAiCategory(incident.getAiCategory());
        response.setAiSuggestedAction(incident.getAiSuggestedAction());
        response.setCreatedAt(incident.getCreatedAt());
        return response;
    }


    public List<IncidentResponse> getIncidentsBySeverity(String severity) {
        return incidentRepository.findByAiSeverityOrderByCreatedAtDesc(severity)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<IncidentResponse> getIncidentsByCategory(String category) {
        return incidentRepository.findByAiCategoryOrderByCreatedAtDesc(category)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<IncidentResponse> getIncidentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return incidentRepository.findByCreatedAtBetween(startDate, endDate)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Add statistics method
    public Map<String, Long> getIncidentStatistics() {
        List<Incident> allIncidents = incidentRepository.findAll();

        Map<String, Long> stats = new HashMap<>();

        // Count by severity
        Map<String, Long> severityStats = allIncidents.stream()
                .collect(Collectors.groupingBy(Incident::getAiSeverity, Collectors.counting()));

        // Count by category
        Map<String, Long> categoryStats = allIncidents.stream()
                .collect(Collectors.groupingBy(Incident::getAiCategory, Collectors.counting()));

        stats.put("total_incidents", (long) allIncidents.size());
        stats.put("high_severity", severityStats.getOrDefault("HIGH", 0L));
        stats.put("medium_severity", severityStats.getOrDefault("MEDIUM", 0L));
        stats.put("low_severity", severityStats.getOrDefault("LOW", 0L));
        stats.put("database_issues", categoryStats.getOrDefault("DATABASE", 0L));
        stats.put("network_issues", categoryStats.getOrDefault("NETWORK", 0L));
        stats.put("application_issues", categoryStats.getOrDefault("APPLICATION", 0L));
        stats.put("infrastructure_issues", categoryStats.getOrDefault("INFRASTRUCTURE", 0L));

        return stats;
    }

}

