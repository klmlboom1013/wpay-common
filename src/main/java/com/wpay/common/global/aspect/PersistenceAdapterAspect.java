package com.wpay.common.global.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class PersistenceAdapterAspect implements BaseAspect {

    @Before("execution(* com.wpay.*.*.adapter.out.external.*Persistence.*Run(..))")
    @Override
    public void before(JoinPoint joinPoint) {

    }

    @After("execution(* com.wpay.*.*.adapter.out.external.*Persistence.*Run(..))")
    @Override
    public void after(JoinPoint joinPoint) {

    }

    @AfterReturning(pointcut = "execution(* com.wpay.*.*.adapter.out.external.*Persistence.*Run(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {

    }

    @AfterThrowing(pointcut = "execution(* com.wpay.*.*.adapter.out.external.*Persistence.*Run(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {

    }
}
