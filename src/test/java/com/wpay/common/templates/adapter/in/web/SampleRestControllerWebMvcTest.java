package com.wpay.common.templates.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpay.common.global.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class SampleRestControllerWebMvcTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void encryptionSampleRun() throws Exception {
        String plainText = "klmlboom";
        ResultActions resultActions = mvc.perform( MockMvcRequestBuilders
                        .post("/crypto/aes/enc")
                        .header("content-type", "application/json")
                        .with(csrf())
                        .content(asJsonString(new SampleRestController.CryptEncryptDto(plainText)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("\n===================");
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
        System.out.println("===================\n");
    }

    @Test
    void decryptionSampleRun() throws Exception {
        String encStr = "WXgfmVsVgsE1BKxVIFdMKg==";
        ResultActions resultActions = mvc.perform( MockMvcRequestBuilders
                        .get("/crypto/aes/dec")
                        .with(csrf())
                        .content(asJsonString(new SampleRestController.CryptEncryptDto(encStr)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("\n===================");
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
        System.out.println("===================\n");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}