package com.applifting.endpoints.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.applifting.endpoints.entity.MonitoredEndpoint;
import com.applifting.endpoints.repository.MonitoredEndpointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MonitoredEndpointServiceTest {
    @InjectMocks
    private MonitoredEndpointService endpointService;

    @Mock
    private MonitoredEndpointRepository endpointRepository;

    @Test
    public void testCreate() {
        MonitoredEndpoint endpoint = new MonitoredEndpoint("endpoint1", "https://www.google.com");
        endpointService.create(endpoint);
        verify(endpointRepository, times(1)).save(endpoint);
    }

    @Test
    public void testReadById() throws ResponseStatusException {
        Integer id = 1;
        MonitoredEndpoint endpoint1 = new MonitoredEndpoint(id,"endpoint1", "https://www.google.com");

        when(endpointRepository.findById(id)).thenReturn(Optional.of(endpoint1));

        MonitoredEndpoint endpoint2 = endpointService.readById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endpoint not found")
        );;

        assertEquals(endpoint1, endpoint2);
        verify(endpointRepository, times(1)).findById(id);
    }

    @Test
    public void testReadAll() {
        List<MonitoredEndpoint> endpoints = new ArrayList<>();
        endpoints.add(new MonitoredEndpoint("endpoint1", "https://www.google.com"));
        endpoints.add(new MonitoredEndpoint("endpoint2", "https://www.seznam.cz"));
        endpoints.add(new MonitoredEndpoint("endpoint3", "https://www.bing.com"));

        when(endpointRepository.findAll()).thenReturn(endpoints);

        Collection<MonitoredEndpoint> endpointList = endpointService.readAll();
        assertEquals(3, endpointList.size());
        verify(endpointRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        MonitoredEndpoint endpoint = new MonitoredEndpoint("endpoint1", "https://www.google.com");

        endpointService.update(endpoint);
        verify(endpointRepository, times(1)).save(endpoint);
    }

    @Test
    public void testDelete() {
        MonitoredEndpoint endpoint = new MonitoredEndpoint("endpoint1", "https://www.google.com");

        endpointService.deleteEntity(endpoint);
        verify(endpointRepository, times(1)).delete(endpoint);
    }
}
