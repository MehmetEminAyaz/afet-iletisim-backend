package com.example.afetsunucu.controller;

import com.example.afetsunucu.dto.StatisticsResponse;
import com.example.afetsunucu.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatisticsResponse> getStats() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}
