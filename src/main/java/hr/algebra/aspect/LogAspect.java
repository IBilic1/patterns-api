package hr.algebra.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @AfterThrowing(pointcut = "execution(* hr.algebra.controller..* (..))", throwing = "ex")
    public void errorInterceptor(Exception ex) {
        logger.error("TEST");
    }
}
