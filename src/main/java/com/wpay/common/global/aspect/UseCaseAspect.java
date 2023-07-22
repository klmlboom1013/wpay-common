package com.wpay.common.global.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component(value = "mobiliansUseCaseAspect")
public class UseCaseAspect implements BaseAspect {
    @Before("execution(* com.wpay.*.*.application.service.*.*UseCase(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.debug("[Before] => {}", joinPoint.getSignature().getName());
    }

    @After("execution(* com.wpay.*.*.application.service.*.*UseCase(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.debug("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.*.*.application.service.*.*UseCase(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.debug("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.*.*.application.service.*.*UseCase(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}