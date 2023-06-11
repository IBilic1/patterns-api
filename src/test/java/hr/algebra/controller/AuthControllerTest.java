package hr.algebra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.PatternsApiApplication;
import hr.algebra.dto.LoginUserDto;
import hr.algebra.dto.TokensDto;
import hr.algebra.dto.UserDto;
import hr.algebra.dto.UserPackageDto;
import hr.algebra.model.Package;
import hr.algebra.model.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PatternsApiApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static TokensDto tokensDto;

    @Test
    void testAuthenticateUser() throws Exception {
        LoginUserDto userDto = new LoginUserDto();
        userDto.setUsername("ivana");
        userDto.setPassword("ivana123");

        MvcResult mvcResult = mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        tokensDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokensDto.class);
    }

    @Test
    void testRefreshToken() throws Exception {
        testAuthenticateUser();
        mockMvc.perform(post("/api/auth/refreshtoken").header("bezkoder-jwt-refresh", tokensDto.getRefreshToken()))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    void testRegisterUser() throws Exception {
        UserPackageDto userPackageDto = new UserPackageDto();
        UserDto userDto = new UserDto();
        userDto.setPassword("abcd123");
        userDto.setUsername("eva123");
        userDto.setEmail("eva123@gmail.com");
        userDto.setRoleId(Roles.USER.getId());

        Package package_ = new Package();
        package_.setId(15L);
        userPackageDto.setDateTime(LocalDateTime.now());
        userPackageDto.setRoleId(Roles.USER.getId());

        userPackageDto.setUser(userDto);
        userPackageDto.setPackage_(package_);
        mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPackageDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testLogoutUser() throws Exception {
        mockMvc.perform(post("/api/auth/signout"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}
