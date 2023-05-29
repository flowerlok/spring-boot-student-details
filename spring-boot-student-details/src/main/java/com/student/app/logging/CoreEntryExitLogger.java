package com.student.app.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CoreEntryExitLogger {

	private static final Logger logger = LogManager.getLogger(CoreEntryExitLogger.class);
	 
    @Around("@annotation(EntryExitLogger)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        Object response = null;
        String method = joinPoint.getSignature().toShortString();
        try {
            logger.info("Starting - " + method);
            response = joinPoint.proceed();
            logger.info("Ending - " + method);
            return response;
        } catch (Exception e) {
            logger.error("Exception while invoking method - " + method);
            throw e;
        }
    }
}
