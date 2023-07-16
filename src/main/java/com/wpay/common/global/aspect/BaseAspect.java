package com.wpay.common.global.aspect;

import org.aspectj.lang.JoinPoint;

public interface BaseAspect {

    void before(JoinPoint joinPoint);

    void after(JoinPoint joinPoint);

    void afterReturning(JoinPoint joinPoint, Object result);

    void afterThrowing(JoinPoint joinPoint, Throwable e);
}
