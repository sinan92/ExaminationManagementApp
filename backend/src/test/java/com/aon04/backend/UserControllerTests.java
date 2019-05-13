package com.aon04.backend;

import com.aon04.backend.controllers.UserController;
import com.aon04.backend.models.Role;
import com.aon04.backend.models.User;
import com.aon04.backend.repository.IExamRepository;
import com.aon04.backend.repository.IFinishedExamRepository;
import com.aon04.backend.repository.ILogRepository;
import com.aon04.backend.repository.IUserRepository;
import com.aon04.backend.services.FileService;
import com.aon04.backend.services.ILogService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class UserControllerTests {

    private MockMvc mockMvc;
    private User user;
    private List<User> userList = new ArrayList<>();
    private List<User> studentList = new ArrayList<>();
    private List<User> emptyList = new ArrayList<>();
    private Role studentRole = new Role();
    private Role supervisorRole = new Role();
    private User studentUser = new User();
    private User supervisorUser = new User();
    private User found = new User();


    @Autowired
    private ILogService logService = new LogService();

    @Mock
    private ILogRepository logRepository;

    @Mock
    private FileService fileService;

    @Mock
    private IFinishedExamRepository finishedExamRepository;

    @Mock
    private IExamRepository examRepository;

    @Mock
    private IUserRepository userRepository;


    @InjectMocks
    private UserController userController = new UserController(logService);

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User();
        user.setId(1);
        user.setStudentNumber("1");
        user.setFirstName("John");
        user.setLastName("Doe");

        studentRole.setId(1);
        studentRole.setName("Student");
        supervisorRole.setId(2);
        supervisorRole.setName("Supervisor");

        studentUser.setRole(studentRole);
        supervisorUser.setRole(supervisorRole);

        userList.add(studentUser);
        userList.add(supervisorUser);

        studentList.add(studentUser);

        found.setStudentNumber("1");
        found.setId(1);
        found.setLastName("doe");
        found.setFirstName("John");
        found.setRole(studentRole);

        MockitoAnnotations.initMocks(this);
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void test_get_all_users() throws Exception {

        when(userRepository.findAll()).thenReturn(userList);
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_get_user_by_id() throws Exception {


        when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(found));

        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
        ;

        verify(userRepository, times(1)).findById(1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(userRepository.findById(1)).thenThrow(new NoSuchElementException(""));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findById(1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_get_all_user_fail_404_not_found() throws Exception {
        when(userRepository.findAll()).thenReturn(emptyList);
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_add_user() throws Exception {


        mockMvc.perform(
                post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentNumber\":\"12345678\",\"firstName\":\"John\",\"lastName\":\"Doe\"}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void test_add_non_existing_user() throws Exception {
        mockMvc.perform(
                post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString("")))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test_delete_non_existing_user_by_id() throws Exception {
        when(userController.deleteUser(5)).thenThrow(new NoSuchElementException());
        mockMvc.perform(
                delete("/user/delete/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void test_delete_user_by_id() throws Exception {
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.ofNullable(user));
        mockMvc.perform(
                delete("/user/delete/{id}", user.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void test_get_all_students() throws Exception {

        when(userRepository.findAllStudents()).thenReturn(studentList);
        mockMvc.perform(get("/user/all/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(userRepository, times(1)).findAllStudents();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_get_all_students_returns_404_when_no_user_has_student_role() throws Exception {
        when(userRepository.findAllStudents()).thenReturn(emptyList);
        mockMvc.perform(get("/user/all/student"))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findAllStudents();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void test_get_student_by_number() throws Exception {
        when(userRepository.findUserByStudentNumber("1")).thenReturn(found);
        mockMvc.perform(get("/user/number/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        verify(userRepository, times(1)).findUserByStudentNumber("1");
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    public void test_get_student_by_number_fail() throws Exception {
        when(userRepository.findUserByStudentNumber("1")).thenThrow(new NoSuchElementException(""));
        mockMvc.perform(get("/user/number/1"))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findUserByStudentNumber("1");
        verifyNoMoreInteractions(userRepository);
    }

}