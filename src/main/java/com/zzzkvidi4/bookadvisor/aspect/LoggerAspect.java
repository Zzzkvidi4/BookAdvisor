package com.zzzkvidi4.bookadvisor.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.zzzkvidi4.bookadvisor.annotation.Logged;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;


@Aspect
@Component
public class LoggerAspect {
    @Pointcut(value = "execution(* *.*(..))")
    public void insideAllMethods(){
    }

    @Around(value = "insideAllMethods() && @annotation(logged)")
    public Object invocation(ProceedingJoinPoint pjp, Logged logged) throws Throwable{
        Logger logger = Logger.getLogger(pjp.getThis().getClass().getName());
        Object[] args = pjp.getArgs();
        logger.info(logged.message() + " started with args: " + Arrays.toString(args));
        Object result = pjp.proceed(args);
        logger.info(logged.message() + " finished with result: " + result.toString());
        return result;
    }
}
