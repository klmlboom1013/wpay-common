package com.wpay.common.templates.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.dto.SelfCrypto;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class SampleRestController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/templates/crypto/aes/enc")
    public ResponseEntity<?> encryptionSampleRun(@RequestBody CryptEncryptDto dto) {
        log.info(">> DTO : {} ", dto.toString());
        dto.resetFieldDataCrypto();

        final CompleteDto completeDto = CompleteDto.builder()
                .wtid("testWtid00000")
                .jnoffcId("testJnoffcId00000")
                .build();

        BeanUtils.copyProperties(dto, completeDto);
        log.debug(">>> {}", completeDto.toString());

        return ResponseEntity.ok().body(BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(completeDto)
                .build());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/templates/crypto/aes/dec")
    public ResponseEntity<?> decryptionSampleRun(@RequestBody CryptDecryptDto dto) {
        log.info(">> DTO : {} ", dto.toString());
        dto.resetFieldDataCrypto();

        final CompleteDto completeDto = CompleteDto.builder()
                .wtid("testWtid00011")
                .jnoffcId("testJnoffcId00011")
                .build();
        BeanUtils.copyProperties(dto, completeDto);
        log.debug(">>> {}", completeDto.toString());

        return ResponseEntity.ok().body(BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(completeDto)
                .build());
    }

    @ToString
    @EqualsAndHashCode(callSuper = false)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CryptEncryptDto extends SelfCrypto {
        @JsonProperty("data")
        @Crypto(type = Crypto.Type.ENCRYPTION, algorithm = Crypto.Algorithm.AES, cryptoKey = Crypto.CryptoKey.API) // 암호화
        @Getter
        private String jnoffcUserId;
    }

    @ToString
    @EqualsAndHashCode(callSuper = false)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CryptDecryptDto extends SelfCrypto {
        @JsonProperty("data")
        @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES, cryptoKey = Crypto.CryptoKey.API) // 복호화
        @Getter
        private String jnoffcUserId;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class CompleteDto {
        private String wtid;
        @JsonProperty("mid")
        private String jnoffcId;
        @JsonProperty("data")
        private String jnoffcUserId;
    }
}
