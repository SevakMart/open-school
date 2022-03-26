package app.openschool.usermanagement;

import app.openschool.usermanagement.api.UserGenerator;
import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.mapper.MentorMapper;
import app.openschool.usermanagement.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void registerValidUser() throws Exception {
        when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

        String requestBody =
                "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
        mockMvc
                .perform(
                        post("/api/v1/register").contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void registerInvalidUserName() throws Exception {
        when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

        String requestBody =
                "{ \"firstName\": \"\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
        mockMvc
                .perform(
                        post("/api/v1/register").contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerInvalidUserPassword() throws Exception {
        when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

        String requestBody =
                "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";
        mockMvc
                .perform(
                        post("/api/v1/register").contentType(APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllMentors() throws Exception {
        List<MentorDto> mentorDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mentorDtoList.add(MentorMapper.toMentorDto(UserGenerator.generateUser()));
        }
        Pageable pageable = PageRequest.of(0, 2);
        Page<MentorDto> MentorPage = new PageImpl<>(mentorDtoList, pageable, 5);
        when(userService.findAllMentors(pageable)).thenReturn(MentorPage);
        mockMvc.perform(get("/api/v1/mentors").queryParam("page", "1")
                        .queryParam("size", "2")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
