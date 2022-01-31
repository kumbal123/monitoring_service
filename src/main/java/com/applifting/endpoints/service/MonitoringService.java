package com.applifting.endpoints.service;

import com.applifting.endpoints.entity.MonitoredEndpoint;
import com.applifting.endpoints.entity.MonitoringResult;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

@Service
public class MonitoringService {
    final MonitoredEndpointService endpointService;
    final MonitoringResultService resultService;
    HashSet<MonitoredEndpoint> endpoints;

    public MonitoringService(MonitoredEndpointService endpointService, MonitoringResultService resultService) {
        this.endpointService = endpointService;
        this.resultService = resultService;
        this.endpoints = new HashSet<>();
    }

    public void addEndpoint(final MonitoredEndpoint endpoint) {
        endpoints.add(endpoint);
    }

    public void removeEndpoint(final MonitoredEndpoint endpoint) {
        endpoints.remove(endpoint);
    }

    @Scheduled(fixedDelay = 5000L)
    public void monitorEndpoints() {
        RestTemplate restTemplate = new RestTemplate();

        for(MonitoredEndpoint endpoint : endpoints) {
            MonitoringResult res;
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(endpoint.getUrl(), String.class);
                endpoint.setLatestCheckDate(new Timestamp(new Date().getTime()));
                res = new MonitoringResult(response.getStatusCode().value(), response.getBody());
                resultService.create(res);
                endpointService.addResult(endpoint, res);
                endpointService.update(endpoint);
            } catch (HttpClientErrorException e) {
                res = new MonitoringResult(e.getStatusCode().value(), e.getResponseBodyAsString());
                endpoint.setLatestCheckDate(new Timestamp(new Date().getTime()));
                resultService.create(res);
                endpointService.addResult(endpoint, res);
                endpointService.update(endpoint);
            }
        }
    }
}
