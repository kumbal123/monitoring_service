package com.applifting.endpoints.controller;

import com.applifting.endpoints.entity.MonitoredEndpoint;
import com.applifting.endpoints.entity.MonitoringResult;
import com.applifting.endpoints.service.MonitoredEndpointService;
import com.applifting.endpoints.service.MonitoringService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonitoredEndpointController.class)
public class MonitoredEndpointControllerTest {
    @MockBean
    private MonitoredEndpointService endpointService;

    @MockBean
    private MonitoringService monitoringService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllMonitoredEndpoints() throws Exception {
        List<MonitoredEndpoint> endpoints = new ArrayList<>();
        endpoints.add(new MonitoredEndpoint("endpoint1", "https://www.google.com"));
        endpoints.add(new MonitoredEndpoint("endpoint2", "https://www.seznam.cz"));

        when(endpointService.readAll()).thenReturn(endpoints);

        mockMvc.perform(get("/endpoints/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(jsonPath("$[0].name", Matchers.is("endpoint1")))
            .andExpect(jsonPath("$[0].url", Matchers.is("https://www.google.com")))
            .andExpect(jsonPath("$[1].name", Matchers.is("endpoint2")))
            .andExpect(jsonPath("$[1].url", Matchers.is("https://www.seznam.cz")));
    }

    @Test
    public void testGetMonitoredEndpoint() throws Exception {
        Integer id = 1;
        MonitoredEndpoint endpoint = new MonitoredEndpoint(id,"endpoint1", "https://www.google.com");

        when(endpointService.readById(id)).thenReturn(Optional.of(endpoint));

        mockMvc.perform(get("/endpoints/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is("endpoint1")))
            .andExpect(jsonPath("$.url", Matchers.is("https://www.google.com")));
    }

    @Test
    public void testListLastTenResults() throws Exception {
        Integer id = 1;
        MonitoredEndpoint endpoint = new MonitoredEndpoint(id,"endpoint1", "https://www.google.com");
        Collection<MonitoringResult> results = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            MonitoringResult result = new MonitoringResult(i, 200, "data");
            endpoint.addResult(result);
            if(i > 4) {
                results.add(result);
            }
        }

        when(endpointService.readById(id)).thenReturn(Optional.of(endpoint));
        when(endpointService.listLastTenResults(endpoint)).thenReturn(results);

        mockMvc.perform(get("/endpoints/" + id + "/results"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(10)))
            .andExpect(jsonPath("$[0].id", Matchers.is(5)))
            .andExpect(jsonPath("$[1].id", Matchers.is(6)))
            .andExpect(jsonPath("$[2].id", Matchers.is(7)))
            .andExpect(jsonPath("$[3].id", Matchers.is(8)))
            .andExpect(jsonPath("$[4].id", Matchers.is(9)))
            .andExpect(jsonPath("$[5].id", Matchers.is(10)))
            .andExpect(jsonPath("$[6].id", Matchers.is(11)))
            .andExpect(jsonPath("$[7].id", Matchers.is(12)))
            .andExpect(jsonPath("$[8].id", Matchers.is(13)))
            .andExpect(jsonPath("$[9].id", Matchers.is(14)));
    }

    @Test
    public void testCreateMonitoredEndpoint() throws Exception {
        mockMvc.perform(post("/endpoints/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                    "  \"name\" : \"endpoint1\",\n" +
                    "  \"url\" : \"https://www.google.com\"\n" +
                    "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is("endpoint1")))
            .andExpect(jsonPath("$.url", Matchers.is("https://www.google.com")));

        ArgumentCaptor<MonitoredEndpoint> argumentCaptor = ArgumentCaptor.forClass(MonitoredEndpoint.class);
        verify(endpointService, times(1)).create(argumentCaptor.capture());
        MonitoredEndpoint endpointProvidedToService = argumentCaptor.getValue();

        assertEquals("endpoint1", endpointProvidedToService.getName());
        assertEquals("https://www.google.com", endpointProvidedToService.getUrl());
    }

    @Test
    public void testUpdateMonitoredEndpoint() throws Exception {
        Integer id = 1;
        MonitoredEndpoint endpoint = new MonitoredEndpoint(id,"endpoint1", "https://www.google.com");

        when(endpointService.readById(id)).thenReturn(Optional.of(endpoint));

        mockMvc.perform(put("/endpoints/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                    "  \"id\" : 1,\n" +
                    "  \"name\" : \"endpoint1\",\n" +
                    "  \"url\" : \"https://www.seznam.cz\"\n" +
                    "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is("endpoint1")))
            .andExpect(jsonPath("$.url", Matchers.is("https://www.seznam.cz")));

        ArgumentCaptor<MonitoredEndpoint> argumentCaptor = ArgumentCaptor.forClass(MonitoredEndpoint.class);
        verify(endpointService, times(1)).update(argumentCaptor.capture());
        MonitoredEndpoint endpointProvidedToService = argumentCaptor.getValue();

        assertEquals("endpoint1", endpointProvidedToService.getName());
        assertEquals("https://www.seznam.cz", endpointProvidedToService.getUrl());
    }

    @Test
    public void testDeleteMonitoredEndpoint() throws Exception {
        Integer id = 1;
        MonitoredEndpoint endpoint = new MonitoredEndpoint(id,"endpoint1", "https://www.google.com");

        when(endpointService.readById(not(eq(id)))).thenReturn(Optional.empty());
        when(endpointService.readById(id)).thenReturn(Optional.of(endpoint));

        mockMvc.perform(get("/endpoints/1111"))
            .andExpect(status().isNotFound());

        verify(endpointService, never()).deleteEntity(any());

        mockMvc.perform(delete("/endpoints/" + id))
            .andExpect(status().isOk());

        verify(endpointService, times(1)).deleteEntity(endpoint);
    }
}
