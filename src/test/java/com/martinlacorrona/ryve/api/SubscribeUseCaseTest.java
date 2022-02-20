package com.martinlacorrona.ryve.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.martinlacorrona.ryve.api.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SubscribeUseCaseTest {

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
    void addSubscribe() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/v1/notification/subscribe")
                .param("stationServiceId", String.valueOf(8889))
                .param("fuelTypeId", String.valueOf(8))
                .param("periodInDays", String.valueOf(3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSubscribe() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/notification/subscribe")
                .param("stationServiceId", String.valueOf(8889))
                .param("fuelTypeId", String.valueOf(8))
                .param("periodInDays", String.valueOf(3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());

        String body = resultActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.read(body, "$[0].id");

        mockMvc.perform( MockMvcRequestBuilders
                .delete("/v1/notification/subscribe")
                .param("subscribeNotificationId", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void getSubscribed() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/notification/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void getSubscribedWithoutLogin() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/notification/subscribe")
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
