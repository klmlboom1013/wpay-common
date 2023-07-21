package com.wpay.common.samp.component;

import com.wpay.common.samp.dto.SmsAuthNumbCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class SampComponents {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/")
    public void sampRun() {
        SmsAuthNumbCommand command = new SmsAuthNumbCommand();
        command.setUserNm("d3BheWNvcmVtb2R1bGUwMGrvFq94OmkE1Zfny26ObAhUOQBXSh5w0xABDGvH/ZRkDw5S1CKG/X6uUo57E8jpCKsrPFhT8hFOS8dencmDzDzLkqesJND4fkeV20dRBZlQPjaG+Xk8SRa/BkJeXD0Y34sXWne1bwtD3SBS4BqTew28qPAX2IsD9QYuEjHMoNYabdm85jRHBhm3HXZorB7aZA==");
        command.resetFieldDataCrypto(command);
    }
}
