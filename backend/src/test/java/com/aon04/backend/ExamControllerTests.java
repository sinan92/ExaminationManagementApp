package com.aon04.backend;

import com.aon04.backend.controllers.ExamController;
import com.aon04.backend.models.Exam;
import com.aon04.backend.repository.IExamRepository;
import com.aon04.backend.repository.ILogRepository;
import com.aon04.backend.services.FileService;
import com.aon04.backend.services.LogService;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class ExamControllerTests {

    private MockMvc mockMvc;
    private Exam exam;

    @Mock
    private ILogRepository logRepository;

    @Mock
    private LogService logService;

    @Mock
    private FileService fileService;
    @Mock
    private IExamRepository examRepository;

    @InjectMocks
    private ExamController examController;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
        MockitoAnnotations.initMocks(this);

        exam = new Exam();
        exam.setId(1);

    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void test_get_all_exams() throws Exception {
        Exam exam1 = new Exam();
        Exam exam2 = new Exam();
        List<Exam> examList = Arrays.asList(exam1, exam2);

        when(examRepository.findAll()).thenReturn(examList);
        mockMvc.perform(get("/skelet/exam/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(examRepository, times(1)).findAll();
        verifyNoMoreInteractions(examRepository);
    }

    @Test
    public void test_get_all_exams_returns_404_when_no_exams_are_present() throws Exception {
        List<Exam> emptyList = new ArrayList<>();
        when(examRepository.findAll()).thenReturn(emptyList);
        mockMvc.perform(get("/skelet/exam/all"))
                .andExpect(status().isNotFound());

        verify(examRepository, times(1)).findAll();
        verifyNoMoreInteractions(examRepository);
    }

    @Test
    public void test_get_exam_by_id() throws Exception {
        when(examRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(exam));

        mockMvc.perform(get("/skelet/exam/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        verify(examRepository, times(1)).findById(1);
        verifyNoMoreInteractions(examRepository);
    }

    @Test
    public void test_get_exam_as_object_by_id() throws Exception {
        when(examRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(exam));

        mockMvc.perform(get("/skelet/exam/asobject/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        verify(examRepository, times(1)).findById(1);
        verifyNoMoreInteractions(examRepository);
    }

    @Test
    public void test_add_exam() throws Exception {
        mockMvc.perform(
                post("/skelet/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(exam)))
                .andExpect(status().isCreated());

    }

    @Test
    public void test_add_non_existing_exam() throws Exception {
        mockMvc.perform(
                post("/skelet/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString("")))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test_find_by_non_existing_id() throws Exception {

        mockMvc.perform(get("/skelet/exam/{id}", 1))
                .andExpect(status().isNotFound())
        ;

    }

}