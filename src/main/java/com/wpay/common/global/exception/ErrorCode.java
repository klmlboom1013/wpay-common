package com.wpay.common.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST 잘못된 요청
    HTTP_STATUS_400(HttpStatus.BAD_REQUEST, "요청 파라미터 값을 다시 확인해 주세요."),
    // 403 FORBIDDEN URI 및 권한은 이상 없으나 서버 접근이 거부됨. (ppcd, wpaytoken, wpayuserkey 등 상테 값이 00이 아닌 경우 또는 인증토큰 유효성 검증 실패)
    HTTP_STATUS_403(HttpStatus.FORBIDDEN, "접근이 거부된 세션 입니다. 잠시 후 다시 시도 바랍니다."),
    // 404 NOT_FOUND 잘못된 리소스 접근
    HTTP_STATUS_404(HttpStatus.NOT_FOUND, "잘못된 리소스 접근 입니다."),
    // 405 METHOD NOT ALLOWED
    HTTP_STATUS_405(HttpStatus.METHOD_NOT_ALLOWED, "지원 하지 않는 Method 입니다."),
    // 408 REQUEST_TIMEOUT 타임아웃 (인증 토큰 사용 시간 만료)
    HTTP_STATUS_408(HttpStatus.REQUEST_TIMEOUT, "요청 시간이 초과 되었습니다."),
    // 415 UNSUPPORTED_MEDIA_TYPE 지원 하지 않는 미디어 타입 (HTTP Request Header: content-type)
    HTTP_STATUS_415(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원 하지 않는 미디어 타입 입니다."),

    // 500 INTERNAL SERVER ERROR
    HTTP_STATUS_500(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 오류가 발생 했습니다. 잠시 후 다시 시도 바랍니다."),
    // 501 BAD_GATEWAY (대외계 처리 응답 결과 오류 코드, WPAY에서 유효하지 않은 데이터로 요청한 경우)
    HTTP_STATUS_501(HttpStatus.BAD_GATEWAY, "대외계 연동 구간 처리 중 오류가 발생 했습니다. 오류가 지속 발생할 경우 관리자에게 문의 부탁 드립니다."),
    // 503 SERVICE UNAVAILABLE (대외계 처리 중 내부 오류 발생.[대외계 서버 작업 또는 프로세스 오류] )
    HTTP_STATUS_503(HttpStatus.SERVICE_UNAVAILABLE, "대왜계 서버 작업 영향으로 서비스 이용이 원활 하지 않습니다. 오류가 지속 발생할 경우 관리자에게 문의 부탁 드립니다."),
    // 504 GATEWAY TIMEOUT [connection timeout/refused, read timeout, write timeout]
    HTTP_STATUS_504(HttpStatus.GATEWAY_TIMEOUT, "대외계 통신 연결이 원뢀 하지 않습니다. 잠시 후 다시 시도 바랍니다. 오류가 지속 발생할 경우 관리자에게 문의 부탁 드립니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
