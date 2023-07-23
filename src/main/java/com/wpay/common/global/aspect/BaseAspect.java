package com.wpay.common.global.aspect;

import com.wpay.common.global.common.Functions;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.SelfCrypto;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
public abstract class BaseAspect {

    public abstract void before(JoinPoint joinPoint);
    public abstract void after(JoinPoint joinPoint);
    public abstract void afterReturning(JoinPoint joinPoint, Object result);
    public abstract void afterThrowing(JoinPoint joinPoint, Throwable e);

    protected void validateCryptoSelf(JoinPoint joinPoint, boolean cryptoFlag){
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof SelfValidating))) return false;
            final SelfValidating<?> selfValidating = (SelfValidating<?>) o;
            /* validation Crypto check */
            if(cryptoFlag)
                selfValidating.validateCryptoSelf();
            else
                selfValidating.validateSelf();
            log.info("Validation check success!! \n[{}]", o.toString());
            return true;
        });
    }

    protected void cryptoSelf(JoinPoint joinPoint){
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof SelfCrypto))) return false;
            final SelfCrypto selfCrypto = (SelfCrypto) o;
            /* validation Crypto check */
            selfCrypto.resetFieldDataCrypto();
            log.info("Validation check success!! \n[{}]", o.toString());
            return true;
        });
    }

    protected void baseCommandValidateCryptoSelf (JoinPoint joinPoint, HttpServletRequest request) {
        Arrays.stream(joinPoint.getArgs()).anyMatch(o -> {
            /* SelfCrypto 을 상속 받지 않았으면 continue */
            if ((Boolean.FALSE.equals(o instanceof BaseCommand))) return false;
            log.info("RequestBody Command : \n[{}]", o.toString());
            final BaseCommand<?> baseCommand = (BaseCommand<?>) o;
            /* API Call Version 확인 */
            baseCommand.checkVersion(request.getRequestURI().split("/")[2]);
            /* set serverName */
            baseCommand.setServerName(Functions.getIdcDvdCd.apply(request.getServerName()));
            /* validation check */
            baseCommand.validateCryptoSelf();
            log.info("Validation check success!! \n[{}]", o.toString());
            return true;
        });
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
        log.info("Result Object body Name: {}", body.getClass().getSimpleName());
        // Body 에 path 세팅
        final Class<?> clazz = body.getClass();
        Arrays.stream(clazz.getDeclaredMethods()).iterator().forEachRemaining(f ->{
            if("setPath".equals(f.getName())) {
                try {
                    f.invoke(body, request.getRequestURI());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());
                    throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "ResponseEntity.body 에 path 세팅 중 오류가 발생 했습니다.", e);
                }
            }
        });
    }
}
