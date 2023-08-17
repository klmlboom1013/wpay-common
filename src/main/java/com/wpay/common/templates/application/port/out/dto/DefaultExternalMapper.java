package com.wpay.common.templates.application.port.out.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;

/**
 * Adapter.out.* 실행 결과를 UseCase 로 Return 하는 DTO
 */
@Log4j2
@Getter
@Value
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class DefaultExternalMapper {
    String eventId;
    Object data;
}
