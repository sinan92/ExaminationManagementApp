package com.aon04.backend;


import com.aon04.backend.controllers.LogController;
import com.aon04.backend.models.Exam;
import com.aon04.backend.models.Log;
import com.aon04.backend.repository.ILogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class LogControllerTest {


    private MockMvc mockMvc;
    private Exam exam;
    private List<Log> emptyList = new ArrayList<>();
    private List <Log> logList = new ArrayList<>();

    @Mock
    private ILogRepository logRepository;

    @InjectMocks
    private LogController logController;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(logController).build();

        MockitoAnnotations.initMocks(this);

        Log log1 = new Log();
        Log log2 = new Log();

        logList.add(log1);
        logList.add(log2);


    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void test_get_all_logs() throws Exception {
                when(logRepository.findAll()).thenReturn(logList);

        mockMvc.perform(get("/logs/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(logRepository, times(1)).findAll();
        verifyNoMoreInteractions(logRepository);
    }

    @Test
    public void test_get_all_logs_fail_404_not_found() throws Exception {
        when(logRepository.findAll()).thenReturn(emptyList);
        mockMvc.perform(get("/logs/all"))
                .andExpect(status().isNotFound());

        verify(logRepository, times(1)).findAll();
        verifyNoMoreInteractions(logRepository);
    }
}

