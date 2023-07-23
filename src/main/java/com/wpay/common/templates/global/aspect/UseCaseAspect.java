package com.wpay.common.templates.global.aspect;

import com.wpay.common.global.aspect.BaseAspect;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component(value = "defaultUseCaseAspect")
public class UseCaseAspect extends BaseAspect {
    @Before("execution(* com.wpay.common.templates.application.service.*Service.*Run(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.debug("[Before] => {}", joinPoint.getSignature().getName());
    }

    @After("execution(* com.wpay.common.templates.application.service.*Service.*Run(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.debug("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.common.templates.application.service.*Service.*Run(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.debug("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.common.templates.application.service.*Service.*Run(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
