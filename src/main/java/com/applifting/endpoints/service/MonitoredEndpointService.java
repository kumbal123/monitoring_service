package com.applifting.endpoints.service;

import com.applifting.endpoints.entity.MonitoredEndpoint;
import com.applifting.endpoints.entity.MonitoringResult;
import com.applifting.endpoints.repository.MonitoredEndpointRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MonitoredEndpointService {
    final MonitoredEndpointRepository repository;

    public MonitoredEndpointService(final MonitoredEndpointRepository repository) {
        this.repository = repository;
    }

    public void create(final MonitoredEndpoint entity) {
        repository.save(entity);
    }

    public Optional<MonitoredEndpoint> readById(Integer id) {
        return repository.findById(id);
    }

    public Collection<MonitoredEndpoint> readAll() {
        return repository.findAll();
    }

    public void update(final MonitoredEndpoint entity) {
        repository.save(entity);
    }

    public void deleteEntity(final MonitoredEndpoint entity) {
        repository.delete(entity);
    }

    public void addResult(final MonitoredEndpoint entity, final MonitoringResult result) {
        entity.addResult(result);
    }

    public Collection<MonitoringResult> listLastTenResults(final MonitoredEndpoint entity) {
        return entity.listLastTenResults();
    }
}
