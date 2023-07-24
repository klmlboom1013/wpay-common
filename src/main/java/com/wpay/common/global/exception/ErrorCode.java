package com.wpay.common.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST 잘못된 요청
    HTTP_STATUS_400(HttpStatus.BAD_REQUEST, "파라미터 값을 확인해 주세요."),
    // 403 FORBIDDEN URI 및 권한은 이상 없으나 서버에서 접근이 거부됨. (ppcd, wpaytoken, wpayuserkey 등 상테값이 00이 아닌 경우 또는 인증토큰 유효성 검증 실패)
    HTTP_STATUS_403(HttpStatus.FORBIDDEN, "서버에서 접근이 거부 되었습니다."),
    // 404 NOT_FOUND 잘못된 리소스 접근
    HTTP_STATUS_404(HttpStatus.NOT_FOUND, "잘못된 리소스 접근 입니다."),
    // 405 METHOD NOT ALLOWED
    HTTP_STATUS_405(HttpStatus.METHOD_NOT_ALLOWED, "지원 하지 않는 Http Method 입니다."),
    // 408 REQUEST_TIMEOUT 타임아웃 (인증토큰 사용 시간 만료)
    HTTP_STATUS_408(HttpStatus.REQUEST_TIMEOUT, "요청 시간이 초과 되었습니다."),
    // 415 UNSUPPORTED_MEDIA_TYPE 지원하지 않는 마디어타입 (HTTP Request Header: content-type)
    HTTP_STATUS_415(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 미디어타입 입니다."),

    // 500 INTERNAL SERVER ERROR
    HTTP_STATUS_500(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 오류가 발생 했습니다. 잠시 후 다시 시도 바랍니다."),
    // 501 BAD_GATEWAY
    HTTP_STATUS_501(HttpStatus.BAD_GATEWAY, "대외계 연동 요청 처리 응답 결과 실패."),
    // 503 SERVICE UNAVAILABLE
    HTTP_STATUS_503(HttpStatus.SERVICE_UNAVAILABLE, "대왜계 서버 작업 영향으로 서비스 이용이 불가능 합니다."),
    // 504 GATEWAY TIMEOUT
    HTTP_STATUS_504(HttpStatus.GATEWAY_TIMEOUT, "대외계 연동 연결 시간이 초과 되었습니다. 잠시 후 다시 시도 바랍니다.")
    ;

    /*






    */


    private final HttpStatus status;

    private final String message;
}
