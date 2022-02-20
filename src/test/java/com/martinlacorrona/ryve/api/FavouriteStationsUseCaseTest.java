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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FavouriteStationsUseCaseTest {

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
    void addFavourite() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/v1/userfavouritestation")
                .param("idStation", String.valueOf(10000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFavourite() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/v1/userfavouritestation")
                .param("idStation", String.valueOf(10000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                .delete("/v1/userfavouritestation")
                .param("idStation", String.valueOf(10000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonExistFavourite() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/v1/userfavouritestation")
                .param("idStation", String.valueOf(1)) //non exist
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void addNotExistFavourite() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/v1/userfavouritestation")
                .param("idStation", String.valueOf(5826))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getFavourites() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/userfavouritestation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString((mail + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
