package com.wpay.common.samp.component;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.dto.SelfCrypto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class SampComponents {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/crypto/aes/enc")
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
        // 암호화
        @Crypto(type = Crypto.Type.ENCRYPTION, algorithm = Crypto.Algorithm.AES256)
        @Getter
        private String data;
    }

    @ToString
    @EqualsAndHashCode(callSuper = false)
    static class CryptDecryptDto extends SelfCrypto {
        // 복호화
        @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES256)
        @Getter
        private String data;
    }
}
