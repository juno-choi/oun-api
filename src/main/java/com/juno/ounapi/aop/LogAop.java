package com.juno.ounapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAop {
    @Around(value = "execution(* com.juno.ounapi.service..*.*(..))")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
        log.info("service call = {}", pjp.getSignature().toShortString());
        Object proceed = pjp.proceed();
        return proceed;
    }

    @Around(value = "execution(* com.juno.ounapi.controller..*.*(..))")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        log.info("controller call = {}", pjp.getSignature().toShortString());
        Object proceed = pjp.proceed();
        return proceed;
    }
}
