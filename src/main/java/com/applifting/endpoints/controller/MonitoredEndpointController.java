package com.applifting.endpoints.controller;

import com.applifting.endpoints.entity.MonitoredEndpoint;
import com.applifting.endpoints.entity.MonitoringResult;
import com.applifting.endpoints.service.MonitoredEndpointService;
import com.applifting.endpoints.service.MonitoringService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/endpoints")
public class MonitoredEndpointController {
    final MonitoredEndpointService endpointService;
    final MonitoringService monitoringService;

    public MonitoredEndpointController(MonitoredEndpointService endpointService, MonitoringService monitoringService) {
        this.endpointService = endpointService;
        this.monitoringService = monitoringService;
    }

    @GetMapping("/")
    public Collection<MonitoredEndpoint> getAllMonitoredEndpoints() {
        return endpointService.readAll();
    }

    @GetMapping("/{id}")
    public MonitoredEndpoint getMonitoredEndpoint(@PathVariable Integer id) {
        return endpointService.readById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endpoint not found")
        );
    }

    @GetMapping("/{id}/results")
    public Collection<MonitoringResult> listLastTenResults(@PathVariable Integer id) {
        MonitoredEndpoint endpoint = endpointService.readById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endpoint not found")
        );

        return endpointService.listLastTenResults(endpoint);
    }

    @PostMapping("/")
    public MonitoredEndpoint createMonitoredEndpoint(@RequestBody MonitoredEndpoint newEndpoint) {
        endpointService.create(newEndpoint);
        monitoringService.addEndpoint(newEndpoint);
        return newEndpoint;
    }

    @PutMapping("/{id}")
    public MonitoredEndpoint updateMonitoredEndpoint(@PathVariable Integer id,
                                                     @RequestBody MonitoredEndpoint newEndpoint) {
        MonitoredEndpoint endpoint = endpointService.readById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endpoint not found")
        );

        monitoringService.removeEndpoint(endpoint);
        endpointService.update(newEndpoint);
        monitoringService.addEndpoint(newEndpoint);

        return newEndpoint;
    }

    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable Integer id) {
        MonitoredEndpoint endpoint = endpointService.readById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endpoint not found")
        );
        monitoringService.removeEndpoint(endpoint);
        endpointService.deleteEntity(endpoint);
    }
}
