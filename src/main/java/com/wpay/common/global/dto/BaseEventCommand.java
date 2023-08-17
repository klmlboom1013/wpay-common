package com.wpay.common.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wpay.common.global.enums.JobCodes;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Log4j2
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Getter
public abstract class BaseEventCommand<T> extends SelfValidating<T> {

    @JsonProperty("mid")
    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(min=10, max=20, message = "길이는 10~20 까지 입니다.")
    private String jnoffcId;

    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(max = 64, message = "길이는 최대 64 자리 입니다.")
    private String wtid;

    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    String version;

    @Setter
    private String idcDvdCd;

    @Setter
    private ResultEventData resultEventData;



    @Override
    public void validateSelf() {
        super.validateSelf();
    }

    /** API URL @PathVariable "version" 값 검증. */
    public abstract void checkVersion();

    /** MobiliansJobCode 리턴 */
    public abstract JobCodes getJobCodes();


    @Getter
    @Builder
    @Value
    @AllArgsConstructor
    @ToString
    public static class ResultEventData {
        String resultCode;
        String resultMsg;
        Object data;
    }
}
