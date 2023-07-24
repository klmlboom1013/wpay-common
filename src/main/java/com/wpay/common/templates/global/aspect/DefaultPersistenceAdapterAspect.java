package com.wpay.common.templates.global.aspect;

import com.wpay.common.global.aspect.BaseAspect;
import com.wpay.common.global.dto.SelfCrypto;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component(value = "defaultPersistenceAdapterAspect")
public class DefaultPersistenceAdapterAspect extends BaseAspect {

    @Before("execution(* com.wpay.common.templates.adapter.out.persistence.*Persistence.*Run(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.info("[Before] => {}", joinPoint.getSignature().getName());
        this.validateCryptoSelf(joinPoint, true);
    }

    @After("execution(* com.wpay.common.templates.adapter.out.persistence.*Persistence.*Run(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.info("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.common.templates.adapter.out.persistence.*Persistence.*Run(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] => {}", joinPoint.getSignature().getName());
        if(result instanceof SelfCrypto) ((SelfCrypto) result).resetFieldDataCrypto();
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.common.templates.adapter.out.persistence.*Persistence.*Run(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
