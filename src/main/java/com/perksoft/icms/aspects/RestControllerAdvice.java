package com.perksoft.icms.aspects;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class RestControllerAdvice {
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMapping() { }
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping() { }
	
	
	@Before(value = "getMapping() || postMapping()", argNames = "joinPoint")
	public void before(JoinPoint joinPoint) {
		addMDC();
		String packageName = joinPoint.getSignature().getDeclaringTypeName();
	    String methodName = joinPoint.getSignature().getName();
		log.info("Entering  - {} - {}()", packageName, methodName);
	}
	
	@AfterReturning(value = "getMapping() || postMapping()", argNames = "joinPoint")
	public void after(JoinPoint joinPoint) {
		String packageName = joinPoint.getSignature().getDeclaringTypeName();
	    String methodName = joinPoint.getSignature().getName();
		log.info("Exit  - {} - {}()", packageName, methodName);
		removeMDC();
	}
	
	private void addMDC() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String traceId = request.getHeader("trace-id");
		
		if(StringUtils.isBlank(traceId)) {
			traceId = UUID.randomUUID().toString();
		}
		MDC.put("trace-id", traceId);
	}
	
	private void removeMDC() {
		MDC.remove("trace-id");
	}

}
