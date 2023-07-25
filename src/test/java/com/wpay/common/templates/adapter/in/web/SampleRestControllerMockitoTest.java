package com.wpay.common.templates.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

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
        SampleRestController.CryptEncryptDto resultDto = (SampleRestController.CryptEncryptDto) Objects.requireNonNull(responseEntity.getBody());
        System.out.println("===================");
        System.out.println(resultDto.getData());
        System.out.println("===================");
    }

    @Test
    void decryptionSampleRun() {
        final String encStr = "d3BheWNvcmVtb2R1bGUwMN9SJ9Q3Ma62hvJgR3Wuwu+Bjvkb/g8onMJ9MIyTkO7ba6VKESRr0/rzHOlyjtsvC1v6kFHeiFJ3kM29tVytTX+nKpulAaIUO5qJlkUCapU5/T9xRNuLPkGm5YunVS7p1zU6I7epk1HKI9VQq3tRe8JfIDlSloD0u0zppPkQtuEPOzZe+2PdVwzkF+I2x3UAkg==";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        SampleRestController.CryptDecryptDto cryptDecryptDto = new SampleRestController.CryptDecryptDto(encStr);
        ResponseEntity<?> responseEntity = this.sampleRestController.decryptionSampleRun(cryptDecryptDto);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        SampleRestController.CryptDecryptDto resultDto = (SampleRestController.CryptDecryptDto) Objects.requireNonNull(responseEntity.getBody());
        System.out.println("===================");
        System.out.println(resultDto.getData());
        System.out.println("===================");
    }
}