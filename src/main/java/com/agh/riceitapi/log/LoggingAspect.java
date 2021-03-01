package com.agh.riceitapi.log;

import com.agh.riceitapi.security.UserPrincipal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.agh.riceitapi.log.LogExecutionTime)")
    public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        // Get information about method class and method names
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        // Get information about user
        long userId = -1;
        Object[] signatureArgs = proceedingJoinPoint.getArgs();
        for (Object o : signatureArgs){
            if (o instanceof UserPrincipal) userId = ((UserPrincipal) o).getId();
        }

        // Measure method execution time
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        // Log method execution time
        logger.info("{} -> {}: {} [ms]", className, methodName, stopWatch.getTotalTimeMillis());

        // Log information about user
        if(userId >= 0){
            logger.info("Operation for user: [{}]\n", userId);
        }

        return result;
    }
}
