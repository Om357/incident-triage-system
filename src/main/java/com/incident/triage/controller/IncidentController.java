package com.incident.triage.controller;

import com.incident.triage.dto.IncidentRequest;
import com.incident.triage.dto.IncidentResponse;
import com.incident.triage.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/incidents")
@Validated
public class IncidentController {


    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }


    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(@Valid @RequestBody IncidentRequest request) {
        IncidentResponse response = incidentService.createIncident(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getAllIncidents() {
        List<IncidentResponse> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getIncidentById(@PathVariable Long id) {
        return incidentService.getIncidentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<IncidentResponse>> getIncidentsBySeverity(@PathVariable String severity) {
        // Validate severity value
        if (!Arrays.asList("HIGH", "MEDIUM", "LOW").contains(severity.toUpperCase())) {
            return ResponseEntity.badRequest().build();
        }

        List<IncidentResponse> incidents = incidentService.getIncidentsBySeverity(severity.toUpperCase());
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<IncidentResponse>> getIncidentsByCategory(@PathVariable String category) {
        // Validate category value
        List<String> validCategories = Arrays.asList("DATABASE", "NETWORK", "APPLICATION", "INFRASTRUCTURE");
        if (!validCategories.contains(category.toUpperCase())) {
            return ResponseEntity.badRequest().build();
        }

        List<IncidentResponse> incidents = incidentService.getIncidentsByCategory(category.toUpperCase());
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<IncidentResponse>> getIncidentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().build();
        }

        List<IncidentResponse> incidents = incidentService.getIncidentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getIncidentStatistics() {
        Map<String, Long> stats = incidentService.getIncidentStatistics();
        return ResponseEntity.ok(stats);
    }

    // Enhanced endpoint with filtering support
    @GetMapping("/search")
    public ResponseEntity<List<IncidentResponse>> searchIncidents(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String affectedService) {

        List<IncidentResponse> allIncidents = incidentService.getAllIncidents();

        // Apply filters if provided
        Stream<IncidentResponse> filteredStream = allIncidents.stream();

        if (severity != null && !severity.isEmpty()) {
            filteredStream = filteredStream.filter(i -> i.getAiSeverity().equalsIgnoreCase(severity));
        }

        if (category != null && !category.isEmpty()) {
            filteredStream = filteredStream.filter(i -> i.getAiCategory().equalsIgnoreCase(category));
        }

        if (affectedService != null && !affectedService.isEmpty()) {
            filteredStream = filteredStream.filter(i ->
                    i.getAffectedService().toLowerCase().contains(affectedService.toLowerCase()));
        }

        List<IncidentResponse> filteredIncidents = filteredStream.collect(Collectors.toList());
        return ResponseEntity.ok(filteredIncidents);
    }
}
