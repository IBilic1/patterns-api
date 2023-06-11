package hr.algebra.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Aspect
public class UserLoggedAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @After("execution(* hr.algebra.controller.AuthController.authenticateUser(..))")
    public void logAfter(JoinPoint joinPoint) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Object[] signatureArgs = joinPoint.getArgs();

        for (Object signatureArg : signatureArgs) {
            Field username = Class.forName(signatureArg.getClass().getName()).getDeclaredField("username");
            username.setAccessible(true);
            logger.info(username.get(signatureArg) + " signed in successfully");
        }
    }
}
