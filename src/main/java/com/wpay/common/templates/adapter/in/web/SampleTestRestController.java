package com.wpay.common.templates.adapter.in.web;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.dto.SelfCrypto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class SampleTestRestController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/crypto/aes/enc")
    public ResponseEntity<?> sampRun(@RequestBody CryptEncryptDto dto) {
        log.info(">> DTO : {} ", dto.toString());
        dto.resetFieldDataCrypto();
        return ResponseEntity.ok().body(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/crypto/aes/dec")
    public ResponseEntity<?> sampRun(@RequestBody CryptDecryptDto dto) {
        log.info(">> DTO : {} ", dto.toString());
        dto.resetFieldDataCrypto();
        return ResponseEntity.ok().body(dto);
    }

    @ToString
    @EqualsAndHashCode(callSuper = false)
    static class CryptEncryptDto extends SelfCrypto {
        @Crypto(type = Crypto.Type.ENCRYPTION, algorithm = Crypto.Algorithm.AES256) // 암호화
        @Getter
        private String data;
    }

    @ToString
    @EqualsAndHashCode(callSuper = false)
    static class CryptDecryptDto extends SelfCrypto {
        @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES256) // 복호화
        @Getter
        private String data;
    }
}
