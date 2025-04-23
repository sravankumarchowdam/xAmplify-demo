package com.xamplify.demo.infrastructure.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging entry, exit and exceptions of methods.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("within(com.xamplify.demo..*)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering: {}", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "within(com.xamplify.demo..*)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exiting: {} with result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "within(com.xamplify.demo..*)", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in: {}", joinPoint.getSignature(), error);
    }
}