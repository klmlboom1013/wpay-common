package com.wpay.common.templates.adapter.in.web;

import com.wpay.common.global.annotation.WebAdapter;
import com.wpay.common.global.port.PortInFactory;
import com.wpay.common.templates.application.port.in.dto.DefaultCommand;
import com.wpay.common.templates.application.port.in.usecase.DefaultUseCaseVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@WebAdapter
@RestController
@RequiredArgsConstructor
class DefaultWebController {

    private final PortInFactory portInFactory;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/templates/{version}/default")
    ResponseEntity<?> defaults (@PathVariable String version, @RequestBody DefaultCommand command) {

        Object result = portInFactory.getUseCasePort(
                DefaultUseCaseVersion.getInstance(version).toString(),
                command.getJobCodes().toString()).execute(command);

        return ResponseEntity.ok().body(result);
    }
}
