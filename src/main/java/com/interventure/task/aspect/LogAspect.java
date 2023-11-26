package com.interventure.task.aspect;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author bojana
 */
@Component
@Aspect
@Log4j2
public class LogAspect {


    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPoincut() {
        // this pointcut identifies public methods
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
    public void beforeRestControllerAdvice(final JoinPoint joinPoint) {
        ThreadContext.clearAll();

        ThreadContext.put("request-id", UUID.randomUUID().toString());

        final HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        ThreadContext.put("host", request.getRemoteHost());
        ThreadContext.put("request-method", request.getMethod());
        ThreadContext.put("request-uri", request.getRequestURI());
        ThreadContext.put("request-query_params", request.getQueryString());
        ThreadContext.put("method-name", joinPoint.getSignature().getName());

    }

   

}
