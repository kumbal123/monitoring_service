package com.applifting.endpoints.service;

import com.applifting.endpoints.repository.MonitoringResultRepository;
import com.applifting.endpoints.entity.MonitoringResult;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class MonitoringResultService {
    final MonitoringResultRepository repository;

    public MonitoringResultService(MonitoringResultRepository repository) {
        this.repository = repository;
    }

    public void create(MonitoringResult entity) {
        repository.save(entity);
    }

    public Optional<MonitoringResult> readById(Integer id) {
        return repository.findById(id);
    }

    public Collection<MonitoringResult> readAll() {
        return repository.findAll();
    }

    public void update(MonitoringResult entity) {
        repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public void deleteEntity(MonitoringResult entity) {
        repository.delete(entity);
    }
}
