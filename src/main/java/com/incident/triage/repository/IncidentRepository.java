package com.incident.triage.repository;

import com.incident.triage.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByAiSeverityOrderByCreatedAtDesc(String severity);
    List<Incident> findByAiCategoryOrderByCreatedAtDesc(String category);
    List<Incident> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
