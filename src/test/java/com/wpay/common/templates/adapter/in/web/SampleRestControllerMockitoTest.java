package com.wpay.common.templates.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class SampleRestControllerMockitoTest {

    @InjectMocks
    SampleRestController sampleRestController;

    @Test
    void encryptionSampleRun() {
        final String plainText = "klmlboom";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        SampleRestController.CryptEncryptDto cryptEncryptDto = new SampleRestController.CryptEncryptDto(plainText);
        ResponseEntity<?> responseEntity = this.sampleRestController.encryptionSampleRun(cryptEncryptDto);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        System.out.println("===================");
        System.out.println(responseEntity.getBody());
        System.out.println("===================");
    }

    @Test
    void decryptionSampleRun() {
        final String encStr = "WXgfmVsVgsE1BKxVIFdMKg==";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        SampleRestController.CryptDecryptDto cryptDecryptDto = new SampleRestController.CryptDecryptDto(encStr);
        ResponseEntity<?> responseEntity = this.sampleRestController.decryptionSampleRun(cryptDecryptDto);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        System.out.println("===================");
        System.out.println(responseEntity.getBody());
        System.out.println("===================");
    }
}