package com.wpay.common.global.exception;

import com.wpay.common.global.dto.ErrorNoDataResponse;
import com.wpay.common.global.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
@RestControllerAdvice(basePackages = "com.wpay")
public class CustomExceptionHandler {

    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity<?> handleCustomException(CustomException ex) {
        log.error("[{}][{}] CustomException: status:{} - {}", ex.getMid(), ex.getWtid(), ex.getErrorCode(), ex.getMessage());
        if(Objects.nonNull(ex.getE())) {
            this.logWriteExceptionStackTrace(ex.getE());
        } else {
            this.logWriteExceptionStackTrace(ex);
        }

        /* ex에 wtid 또는 mid가 있다면 ErrorResponseV2로 세팅 한다. */
        if(Strings.isNotBlank(ex.getWtid()) || Strings.isNotBlank(ex.getMid())){
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .httpStatus(ex.getErrorCode().getStatus())
                    .message(Strings.isNotBlank(ex.getMessage()) ? ex.getMessage() : ex.getErrorCode().getMessage())
                    .error(ex.getErrorCode().getStatus().series().name().toLowerCase())
                    .data(ErrorResponse.DefaultData.builder().mid(ex.getMid()).wtid(ex.getWtid()).build())
                    .build();
            return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
        }

        ErrorNoDataResponse errorNoDataResponse = ErrorNoDataResponse.builder()
                .httpStatus(ex.getErrorCode().getStatus())
                .message(Strings.isNotBlank(ex.getMessage()) ? ex.getMessage() : ex.getErrorCode().getMessage())
                .error(ex.getErrorCode().getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorNoDataResponse);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    protected ResponseEntity<?> handleServerException(ConstraintViolationException ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorNoDataResponse errorNoDataResponse = ErrorNoDataResponse.builder()
                .httpStatus(ErrorCode.HTTP_STATUS_400.getStatus())
                .message(Strings.isNotBlank(ex.getMessage()) ? ex.getMessage() : ErrorCode.HTTP_STATUS_400.getMessage())
                .error(ErrorCode.HTTP_STATUS_400.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.HTTP_STATUS_400.getStatus()).body(errorNoDataResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorNoDataResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorNoDataResponse errorNoDataResponse = ErrorNoDataResponse.builder()
                .httpStatus(ErrorCode.HTTP_STATUS_405.getStatus())
                .message(ErrorCode.HTTP_STATUS_405.getMessage())
                .error(ErrorCode.HTTP_STATUS_405.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.HTTP_STATUS_405.getStatus()).body(errorNoDataResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorNoDataResponse> handleNoHandlerFoundExceptionException(NoHandlerFoundException ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorNoDataResponse errorNoDataResponse = ErrorNoDataResponse.builder()
                .httpStatus(ErrorCode.HTTP_STATUS_404.getStatus())
                .message(ErrorCode.HTTP_STATUS_404.getMessage())
                .error(ErrorCode.HTTP_STATUS_404.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.HTTP_STATUS_404.getStatus()).body(errorNoDataResponse);
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<?> handleServerException(Exception ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorNoDataResponse errorNoDataResponse = ErrorNoDataResponse.builder()
                .httpStatus(ErrorCode.HTTP_STATUS_500.getStatus())
                .message(ErrorCode.HTTP_STATUS_500.getMessage())
                .error(ErrorCode.HTTP_STATUS_500.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.HTTP_STATUS_500.getStatus()).body(errorNoDataResponse);
    }

    private void logWriteExceptionStackTrace(Throwable e){
        if(e instanceof CustomException) {
            CustomException ex = (CustomException) e;
            log.error("[{}][{}] CustomException: status:{} - {}", ex.getMid(), ex.getWtid(), ex.getErrorCode(), ex.getMessage());
        } else {
            log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        }
        final StringBuilder sb = new StringBuilder("Print Stack Trace: \n");
        Arrays.stream(e.getStackTrace()).iterator().forEachRemaining(
                se -> sb.append("    ")
                        .append(se.getClassName()).append("(")
                        .append(se.getMethodName()).append(":")
                        .append(se.getLineNumber()).append(")").append("\n"));
        log.error(sb.toString());
    }
}
