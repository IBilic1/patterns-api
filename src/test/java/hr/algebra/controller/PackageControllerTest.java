package hr.algebra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.PatternsApiApplication;
import hr.algebra.dto.LoginUserDto;
import hr.algebra.dto.TokensDto;
import hr.algebra.mapper.PackageDtoMapper;
import hr.algebra.model.Package;
import hr.algebra.service.PackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PatternsApiApplication.class)
@AutoConfigureMockMvc
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthController authController;

    @Mock
    private PackageService packageService;

    @Mock
    private PackageDtoMapper packageDtoMapper;

    @InjectMocks
    private PackageController packageController;

    private static TokensDto tokensDto;

    private List<Package> packages;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(packageController).build();
        testAuthenticateUser();

        Long id = 12345L;
        String title = "Example Title";
        double uploadSize = 10.5;
        int dailyUploadLimit = 5;
        double price = 19.99;

        Package aPackage = new Package(id, title, uploadSize, dailyUploadLimit, price);
        packages = new ArrayList<>();
        packages.add(aPackage);
    }

    void testAuthenticateUser() throws Exception {
        LoginUserDto userDto = new LoginUserDto();
        userDto.setUsername("isus123");
        userDto.setPassword("ISUS123");

        MvcResult mvcResult = MockMvcBuilders.standaloneSetup(authController).build().perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        tokensDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokensDto.class);
    }

    @Test
    void getAllPackages() throws Exception {
        when(packageService.getPackages()).thenReturn(packages);
        MvcResult mvcResult = mockMvc.perform(get("/package").header("bezkoder-jwt", tokensDto.getAccessToken()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
