package com.martinlacorrona.ryve.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martinlacorrona.ryve.api.model.FuelTypeModel;
import com.martinlacorrona.ryve.api.model.UserModel;
import com.martinlacorrona.ryve.api.model.UserPreferencesModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserPreferencesUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    private String mail = "martin@gmail.comm";
    private String password = "12345678a";

    private UserModel userModel = new UserModel();

    @BeforeEach
    public void beforeRegisterUser() throws Exception {
        userModel.setMail(mail);
        userModel.setPassword("12345678a");
        userModel.setName("Martin Test");
        userModel.setSurname("Fernandez Test");

        mockMvc.perform( MockMvcRequestBuilders
                .post("/v1/user/register")
                .content(asJsonString(userModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").isNotEmpty());
    }

    @Test
    void getUserPreferences() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/userpreferences")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void getUserPreferencesWithoutLogin() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/userpreferences")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateUserPreferences() throws Exception {
        FuelTypeModel fuelTypeModel = new FuelTypeModel();
        fuelTypeModel.setId(8L);

        UserPreferencesModel userPreferencesModel = new UserPreferencesModel();
        userPreferencesModel.setCarcolor("Azul");
        userPreferencesModel.setCarname("Azul");
        userPreferencesModel.setFavouriteFuel(fuelTypeModel);
        userPreferencesModel.setKmRange(200.0);

        mockMvc.perform( MockMvcRequestBuilders
                .put("/v1/userpreferences")
                .content(asJsonString(userPreferencesModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserPreferencesIncomplete() throws Exception {
        FuelTypeModel fuelTypeModel = new FuelTypeModel();
        fuelTypeModel.setId(8L);

        UserPreferencesModel userPreferencesModel = new UserPreferencesModel(); //without carcolor and carname
        userPreferencesModel.setFavouriteFuel(fuelTypeModel);
        userPreferencesModel.setKmRange(200.0);

        mockMvc.perform( MockMvcRequestBuilders
                .put("/v1/userpreferences")
                .content(asJsonString(userPreferencesModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateUserPreferencesWithoutLogin() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/v1/userpreferences")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
