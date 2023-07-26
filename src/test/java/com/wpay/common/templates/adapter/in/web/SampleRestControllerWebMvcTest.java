package com.wpay.common.templates.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpay.common.global.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(SampleRestController.class)
@Import(SecurityConfig.class)
class SampleRestControllerWebMvcTest {


    @Autowired
    private MockMvc mvc;


    @Test
    void encryptionSampleRun() throws Exception {
        String plainText = "klmlboom";
        ResultActions resultActions = mvc.perform( MockMvcRequestBuilders
                    .post("/crypto/aes/enc")
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
        String encStr = "d3BheWNvcmVtb2R1bGUwMN9SJ9Q3Ma62hvJgR3Wuwu+Bjvkb/g8onMJ9MIyTkO7ba6VKESRr0/rzHOlyjtsvC1v6kFHeiFJ3kM29tVytTX+nKpulAaIUO5qJlkUCapU5/T9xRNuLPkGm5YunVS7p1zU6I7epk1HKI9VQq3tRe8JfIDlSloD0u0zppPkQtuEPOzZe+2PdVwzkF+I2x3UAkg==";
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