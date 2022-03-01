package com.airfrance.userApp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.airfrance.userApp.controller.UserController;
import com.airfrance.userApp.entity.Gender;
import com.airfrance.userApp.entity.User;
import com.airfrance.userApp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserAppApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> userList;
    
    @Test
    void shouldFetchAllUsers() throws Exception {

        given(userService.getAll()).willReturn(userList);

        this.mockMvc.perform(get("/api/"))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldFetchOneUserById() throws Exception {
        final Long userId = 1L;
        final User user = new User(null, "username", LocalDate.of(2021, 10, 14),"India", "phone_number", Gender.MALE);

        given(userService.getUserById(userId)).willReturn(Optional.of(user));

        this.mockMvc.perform(get("/api/1", userId))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldCreateNewUser() throws Exception {
        given(userService.createUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        User user = new User(null, "username", LocalDate.of(1996, 10, 14),"French", "phone_number", Gender.MALE);

        this.mockMvc.perform(post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }
    

    @Test
    void shouldReturn400WhenCreateNewUserWithoutUserName() throws Exception {
        User user = new User(null, null, LocalDate.of(2021, 10, 14),"India", "phone_number", Gender.MALE);

        this.mockMvc.perform(post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturn400WhenCreateNewUserWithoutCountry() throws Exception {
        User user = new User(null, "username", LocalDate.of(2021, 10, 14),null, "phone_number", Gender.MALE);

        this.mockMvc.perform(post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturn400WhenCreateNewUserWithoutBirthday() throws Exception {
        User user = new User(null, "username", null,"srilanka", "phone_number", Gender.MALE);

        this.mockMvc.perform(post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturn400WhenCreateNewUserNonAdult() throws Exception {
        User user = new User(null, "username", LocalDate.of(2021, 10, 14),"French", "phone_number", Gender.MALE);

        this.mockMvc.perform(post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturn400WhenCreateNewUserNonFrench() throws Exception {
        User user = new User(null, "username", LocalDate.of(1996, 10, 14),"US", "phone_number", Gender.MALE);

        this.mockMvc.perform(post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
