package com.aon04.backend;

import com.aon04.backend.controllers.FinishedExamController;
import com.aon04.backend.models.Exam;
import com.aon04.backend.models.FinishedExam;
import com.aon04.backend.models.User;
import com.aon04.backend.repository.IExamRepository;
import com.aon04.backend.repository.IFinishedExamRepository;
import com.aon04.backend.repository.ILogRepository;
import com.aon04.backend.repository.IUserRepository;
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
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class FinishedExamControllerTests {

    private MockMvc mockMvc;
    private FinishedExam finishedExam;
    private FinishedExam finishedExam1;
    private FinishedExam finishedExam2;
    private Exam exam;
    private User user;
    private List<FinishedExam> finishedExamList;
    private List<FinishedExam> emptyList = new ArrayList<>();


    @Mock
    private ILogRepository logRepository;

    @Mock
    private LogService logService;

    @Mock
    private FileService fileService;

    @Mock
    private IFinishedExamRepository finishedExamRepository;

    @Mock
    private IExamRepository examRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private FinishedExamController finishedExamController;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(finishedExamController).build();
        MockitoAnnotations.initMocks(this);

        finishedExam = new FinishedExam();
        finishedExam.setId(1);
        finishedExam.setFinishedExam("TestExam");

        user = new User();
        user.setId(1);

        exam = new Exam();
        exam.setId(1);

        finishedExam1 = new FinishedExam();
        finishedExam2 = new FinishedExam();
        finishedExamList = Arrays.asList(finishedExam1, finishedExam2);

    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void test_get_all_finished_exams() throws Exception {


        when(finishedExamRepository.findAll()).thenReturn(finishedExamList);
        mockMvc.perform(get("/finished/exam/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(finishedExamRepository, times(1)).findAll();
        verifyNoMoreInteractions(finishedExamRepository);
    }

    @Test
    public void test_get_all_finished_exams_returns_404_when_no_finised_exams_are_present() throws Exception {
        when(finishedExamRepository.findAll()).thenReturn(emptyList);
        mockMvc.perform(get("/finished/exam/all"))
                .andExpect(status().isNotFound());

        verify(finishedExamRepository, times(1)).findAll();
        verifyNoMoreInteractions(finishedExamRepository);
    }

    @Test
    public void test_get_finished_exam_by_id() throws Exception {
        when(finishedExamRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(finishedExam));
        when(fileService.loadAsResource(any())).thenReturn(new UrlResource(new File("/home/toon/test").toURI()));


        mockMvc.perform(get("/finished/exam/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        verify(finishedExamRepository, times(1)).findById(1);
        verifyNoMoreInteractions(finishedExamRepository);
    }

    @Test
    public void test_add_finished_exam() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        mockMvc.perform(
                post("/finished/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(finishedExam)))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_add_non_existing_finished_exam() throws Exception {
        when(examRepository.findById(anyInt())).thenReturn(Optional.ofNullable(exam));
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(
                post("/finished/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString("POST /finished/exam/create HTTP/1.1\n" +
                                "Host: localhost:8080\n" +
                                "Cache-Control: no-cache\n" +
                                "Postman-Token: 5424e78a-dfb3-4264-8e47-8e1fab78dc76\n" +
                                "Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW\n" +
                                "\n" +
                                "------WebKitFormBoundary7MA4YWxkTrZu0gW\n" +
                                "Content-Disposition: form-data; name=\"user_id\"\n" +
                                "\n" +
                                "3\n" +
                                "------WebKitFormBoundary7MA4YWxkTrZu0gW\n" +
                                "Content-Disposition: form-data; name=\"exam_id\"\n" +
                                "\n" +
                                "2\n" +
                                "------WebKitFormBoundary7MA4YWxkTrZu0gW\n" +
                                "Content-Disposition: form-data; name=\"file\"; filename=\"\"\n" +
                                "Content-Type: \n" +
                                "\n" +
                                "\n" +
                                "------WebKitFormBoundary7MA4YWxkTrZu0gW--")))
                .andExpect(status().isBadRequest());

    }

}