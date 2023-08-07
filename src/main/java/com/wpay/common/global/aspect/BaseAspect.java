package com.wpay.common.global.aspect;

import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.functions.DataFunctions;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.SelfCrypto;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public abstract class BaseAspect {

    public abstract void before(JoinPoint joinPoint);
    public abstract void after(JoinPoint joinPoint);
    public abstract void afterReturning(JoinPoint joinPoint, Object result);
    public abstract void afterThrowing(JoinPoint joinPoint, Throwable e);

    protected void validateCryptoSelf(JoinPoint joinPoint, boolean cryptoFlag){
        log.debug("DTO Validation 검증 및 데이터 암복호화를 시작 합니다.");
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof SelfValidating))) return false;
            isPlay.set(true);
            final SelfValidating<?> selfValidating = (SelfValidating<?>) o;
            /* validation Crypto check */
            if(cryptoFlag)
                selfValidating.validateCryptoSelf();
            else
                selfValidating.validateSelf();
            log.info("Validation check success!! \n[{}]", o.toString());
            return true;
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("DTO Validation 검증 및 데이터 암복호화 진행 대상이 없습니다.");
    }

    protected void cryptoSelf(JoinPoint joinPoint){
        log.debug("DTO 데이터 암복호화를 시작 합니다.");
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof SelfCrypto))) return false;
            isPlay.set(true);
            final SelfCrypto selfCrypto = (SelfCrypto) o;
            /* validation Crypto check */
            selfCrypto.resetFieldDataCrypto();
            log.info("Crypto success!! \n[{}]", o.toString());
            return true;
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("DTO 데이터 암복호화 진행 대상이 없습니다.");
    }

    protected void baseCommandValidateCryptoSelf (JoinPoint joinPoint, HttpServletRequest request) {
        log.debug("http header 미디어타입을 검증을 시작 합니다.");
        AtomicReference<String> contentType = new AtomicReference<>();
        request.getHeaderNames().asIterator().forEachRemaining(key -> {
            if(key.equalsIgnoreCase("content-type")){
                contentType.set(request.getHeader(key));
            }
        });
        log.debug(">>> http request header: contentType : {}", contentType.get());
        if(Strings.isBlank(contentType.get()) || Boolean.FALSE.equals(contentType.get().toLowerCase().contains("application/json"))){
            throw new CustomException(
                    CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_415).build());
        }

        log.debug("CommandDTO Validation 검증 및 데이터 암복호화를 시작 합니다.");
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof BaseCommand))) return false;
            isPlay.set(true);
            log.info("RequestBody Command : \n[{}]", o.toString());
            final BaseCommand<?> baseCommand = (BaseCommand<?>) o;
            /* API Call Version 확인 */
            baseCommand.checkVersion(request.getRequestURI().split("/")[2]);
            /* set serverName */
            baseCommand.setServerName(DataFunctions.getIdcDvdCd.apply(request.getServerName()));
            /* validation check */
            baseCommand.validateCryptoSelf();
            log.info("Validation check success!! \n[{}]", o.toString());
            return true;
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("CommandDTO Validation 검증 및 데이터 암복호화 진행 대상이 없습니다.");
    }

    protected void resultSetUriPath(Object result, HttpServletRequest request) {
        if(Objects.isNull(result) || Boolean.FALSE.equals(result instanceof ResponseEntity)) {
            log.warn("Return Object 는 ResponseEntity 가 아니 므로 ResponseEntity.body.path 세팅이 불가능 합니다.");
            return;
        }

        if(Objects.isNull(((ResponseEntity<?>) result).getBody())) {
            log.warn("ResponseEntity 또는 ResponseEntity.body 가 Null 이므로 path 값 세팅이 불가능 합니다.");
            return;
        }

        final ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        final Object body = responseEntity.getBody();
        log.debug("ResponseEntity.body 에 path 세팅을 시작 합니다. [{}]", body.getClass().getSimpleName());
        // Body 에 path 세팅
        final Class<?> clazz = body.getClass();
        AtomicBoolean isPlay = new AtomicBoolean(false);
        Arrays.stream(clazz.getDeclaredMethods()).iterator().forEachRemaining(f ->{
            if("setPath".equals(f.getName())) {
                isPlay.set(true);
                try {
                    f.invoke(body, request.getRequestURI());
                    log.info("ResultDTO API URI Path 세팅 완료. [{}]", body.toString());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    isPlay.set(false);
                    log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());
                    throw new CustomException(
                            CustomExceptionData.builder()
                                    .errorCode(ErrorCode.HTTP_STATUS_500)
                                    .message("ResponseEntity.body 에 path 세팅 중 오류가 발생 했습니다.")
                                    .e(e)
                                    .build());
                }
            }
        });
        if(Boolean.FALSE.equals(isPlay.get()))
            log.debug("ResponseEntity.body 에 path 세팅 대상 method 가 없습니다. [{}]", result.getClass().getSimpleName());
    }
}
