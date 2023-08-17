package com.wpay.common.templates.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpay.common.global.security.SecurityConfig;
import com.wpay.common.templates.application.port.in.dto.DefaultCommand;
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
class DefaultWebControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void defaults() throws Exception {
        final DefaultCommand defaultCommand =
                new DefaultCommand("write..data1", "write..data2",
                        "NW20230817005432101","INIwpayT03");

        ResultActions resultActions = mvc.perform( MockMvcRequestBuilders
                        .post("/templates/v1/default")
                        .header("content-type", "application/json")
                        .with(csrf())
                        .content(asJsonString(defaultCommand))
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