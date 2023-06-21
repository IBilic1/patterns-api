package hr.algebra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.PatternsApiApplication;
import hr.algebra.dto.LoginUserDto;
import hr.algebra.dto.TokensDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PatternsApiApplication.class)
@AutoConfigureMockMvc
public class TagContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static TokensDto tokensDto;

    @BeforeEach
    void setUp() throws Exception {
        testAuthenticateUser();
    }

    void testAuthenticateUser() throws Exception {
        LoginUserDto userDto = new LoginUserDto();
        userDto.setUsername("ivana");
        userDto.setPassword("ivana");

        MvcResult mvcResult = mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        tokensDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokensDto.class);
    }

    @Test
    public void getAllTags() throws Exception {
        mockMvc.perform(get("/tag-content/tags").header("patterns-jwt", tokensDto.getAccessToken()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void getAllTagContent() throws Exception {
        mockMvc.perform(get("/tag-content").header("patterns-jwt", tokensDto.getAccessToken()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}
