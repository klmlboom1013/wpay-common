package com.wpay.common.global.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Log4j2
@Aspect
@Component(value = "baseWebAdapterAspect")
@RequiredArgsConstructor
public class WebAdapterAspect extends BaseAspect {

    private final HttpServletRequest request;

    @Before("execution(* com.wpay..adapter.in.web.*Controller.*Run(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.info("[Before] => {}", joinPoint.getSignature().getName());
        this.baseCommandValidateCryptoSelf(joinPoint, request);
    }

    @After("execution(* com.wpay..adapter.in.web.*Controller.*Run(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.info("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay..adapter.in.web.*Controller.*Run(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(),
                (Objects.nonNull(result)) ? result.toString() : new Object());
        super.afterReturningEncryptResponseBody(result, this.request);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay..adapter.in.web.*Controller.*Run(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
