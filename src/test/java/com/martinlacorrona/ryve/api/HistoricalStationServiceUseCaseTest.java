package com.martinlacorrona.ryve.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martinlacorrona.ryve.api.model.UserModel;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HistoricalStationServiceUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    private String mail = "martin@gmail.comm";
    private String password = "12345678a";

    @BeforeEach
    public void beforeRegisterUser() throws Exception {
        UserModel userModel = new UserModel();
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
    void checkHistoricalPrices() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/stationservice/history")
                .param("fuelTypeId", String.valueOf(6))
                .param("stationServiceId", String.valueOf(10000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void checkHistoricalPricesWithoutLogin() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/stationservice/history")
                .param("fuelTypeId", String.valueOf(6))
                .param("stationServiceId", String.valueOf(10000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void checkHistoricalPricesNonExist() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/stationservice/history")
                .param("fuelTypeId", String.valueOf(8))
                .param("stationServiceId", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}