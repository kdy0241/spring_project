package com.gn.mvc;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	
	private static final Logger LOGGER 
		= LoggerFactory.getLogger(LoggerAspect.class);
	// 모든 메소드들이 해당됨
	@Before("execution(* com.gn.mvc..*(..))")
	public void methodBefore(JoinPoint jp) {
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
//		LOGGER.warn(className+"의 "+methodName+"실행 직전");
	}
	
}
