package com.applifting.endpoints.controller;

import com.applifting.endpoints.entity.MonitoringResult;
import com.applifting.endpoints.service.MonitoringResultService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/results")
public class MonitoringResultController {
    final MonitoringResultService service;

    public MonitoringResultController(MonitoringResultService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Collection<MonitoringResult> getAllMonitoringResults() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public MonitoringResult getMonitoringResult(@PathVariable Integer id) {
        return service.readById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found")
        );
    }

}
