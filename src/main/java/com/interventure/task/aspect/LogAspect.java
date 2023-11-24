package com.interventure.task.aspect;

import java.time.Duration;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 *
 * @author bojana
 */
@Component
@Aspect
@Log4j2
public class LogAspect {

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main
     * packages.
     */
    @Pointcut("within(com.interventure.task..*)"
            + " || within(com.interventure.task.service..*)"
            + " || within(com.interventure.task.controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    
    
  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void controllerPoincut() {
    // this pointcut identifies public methods
  }

    /**
     * Advice that logs when a method is entered.
     *
     * @param joinPoint
     * @throws Throwable throws IllegalArgumentException
     */
    @Before("applicationPackagePointcut() && springBeanPointcut()")
    public void beforeAdvice(JoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        
        ThreadContext.put("className", className);
        ThreadContext.put("methodName", methodName);

 

    }
    
    @Around("controllerPoincut()")
    public Object logExecutionTimeRestController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
 
        Instant startTime = Instant.now();
        Object result = proceedingJoinPoint.proceed();
        long elapsedTime = Duration.between(startTime, Instant.now()).toMillis();

        ThreadContext.put("duration", String.valueOf(elapsedTime));
        
        log.info("Event finished.");
        return result;

    }
    
    
    @Before("controllerPoincut()")
    public void beforeRestControllerAdvice(JoinPoint joinPoint) {
      ThreadContext.clearAll();
}

}
