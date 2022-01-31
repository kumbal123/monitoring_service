package com.applifting.endpoints.controller;

import com.applifting.endpoints.entity.MonitoringResult;
import com.applifting.endpoints.service.MonitoringResultService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonitoringResultController.class)
public class MonitoringResultControllerTest {
    @MockBean
    private MonitoringResultService resultService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllMonitoringResults() throws Exception {
        List<MonitoringResult> results = new ArrayList<>();
        results.add(new MonitoringResult(200, "data1"));
        results.add(new MonitoringResult(404, "data2"));

        Mockito.when(resultService.readAll()).thenReturn(results);

        mockMvc.perform(get("/results/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(jsonPath("$[0].httpStatusCode", Matchers.is(200)))
            .andExpect(jsonPath("$[0].payload", Matchers.is("data1")))
            .andExpect(jsonPath("$[1].httpStatusCode", Matchers.is(404)))
            .andExpect(jsonPath("$[1].payload", Matchers.is("data2")));
    }

    @Test
    public void testGetMonitoringResult() throws Exception {
        Integer id = 1;
        MonitoringResult result = new MonitoringResult(id,200, "data1");

        Mockito.when(resultService.readById(id)).thenReturn(Optional.of(result));

        mockMvc.perform(get("/results/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Matchers.is(id)))
            .andExpect(jsonPath("$.httpStatusCode", Matchers.is(200)))
            .andExpect(jsonPath("$.payload", Matchers.is("data1")));
    }
}
