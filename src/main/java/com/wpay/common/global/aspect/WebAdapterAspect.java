package com.wpay.common.global.aspect;

import com.wpay.common.global.common.Functions;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.BaseValidation;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class WebAdapterAspect implements BaseAspect {

    private final HttpServletRequest request;

    @Before("execution(* com.wpay.*.*.adapter.in.web.*.*(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.info("[Before] => {}", joinPoint.getSignature().getName());

        /* API Call Version */
        final String uriVersion = this.request.getRequestURI().split("/")[2];

        /* Request validation check */
        for(Object o : joinPoint.getArgs()) {
            /* BaseValidation 을 상속 받지 않았으면 continue */
            if( (Boolean.FALSE.equals(o instanceof BaseValidation))) continue;
            log.info("RequestBody Command : \n[{}]", o.toString());

            /* API Command DTO - MobiliansCommand DTO extends 여부 확인 */
            if(Boolean.FALSE.equals(o instanceof BaseCommand))
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "API Command DTO 는 BaseCommand 를 구현 하고 있지 않습니다.");

            final BaseCommand<?> baseCommand = (BaseCommand<?>) o;

            /* API Call Version 확인 */
            if(Boolean.FALSE.equals(baseCommand.checkVersion(uriVersion)))
                throw new CustomException(ErrorCode.INVALID_PARAMETER, "API Url PathVariable \"version\" 오류 입니다. ");

            /* set serverName */
            baseCommand.setServerName(Functions.getIdcDvdCd.apply(request.getServerName()));

            /* validation check */
            ((BaseValidation) o).validateSelf();
            log.info("Validation check success!! \n[{}]", o.toString());
        }
    }

    @After("execution(* com.wpay.*.*.adapter.in.web.*.*(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.info("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.*.*.adapter.in.web.*.*(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
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
                    f.invoke(body, this.request.getRequestURI());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());
                    throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "ResponseEntity.body 에 path 세팅 중 오류가 발생 했습니다.", e);
                }
            }
        });
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.*.*.adapter.in.web.*.*(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
