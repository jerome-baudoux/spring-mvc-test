package com.mvc.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * An aspect that logs the methods with parameter and return type
 * @author Jerome
 */
@Aspect
@Component
public class LoggerAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);
	
	@Around("execution(* *(..)) && @annotation(log))")
	public Object log(final ProceedingJoinPoint pjp, final Log log) throws Throwable {

		if(log.logCall()) {
			LOGGER.debug("Calling method {} with parameters: {}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));
		}
		
		Object returned = pjp.proceed();

		if(log.logReturn()) {
			LOGGER.debug("Called method {} returned: {}", pjp.getSignature(), returned);
		}
		
		return returned;
	}
	
	@Component
	@Target(value = {ElementType.METHOD})
	@Retention(value = RetentionPolicy.RUNTIME)
	public @interface Log {
		
		boolean logCall() default true;
		
		boolean logReturn() default true;
	}
}
