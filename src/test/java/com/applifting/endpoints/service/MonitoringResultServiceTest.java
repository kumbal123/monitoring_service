package com.applifting.endpoints.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.applifting.endpoints.entity.MonitoringResult;
import com.applifting.endpoints.repository.MonitoringResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MonitoringResultServiceTest {
    @InjectMocks
    private MonitoringResultService resultService;

    @Mock
    private MonitoringResultRepository resultRepository;

    @Test
    public void testCreate() {
        MonitoringResult result = new MonitoringResult(200, "data1");
        resultService.create(result);
        verify(resultRepository, times(1)).save(result);
    }

    @Test
    public void testReadAll() {
        List<MonitoringResult> results = new ArrayList<>();
        results.add(new MonitoringResult(200, "data1"));
        results.add(new MonitoringResult(404, "data2"));
        results.add(new MonitoringResult(200, "data3"));

        when(resultRepository.findAll()).thenReturn(results);

        Collection<MonitoringResult> resultList = resultService.readAll();
        assertEquals(3, resultList.size());
        verify(resultRepository, times(1)).findAll();
    }
}
