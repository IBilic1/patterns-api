package hr.algebra.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@Aspect
public class UserLoggedAspect {

    Logger logger = LoggerFactory.getLogger(UserLoggedAspect.class);

    @After("execution(* hr.algebra.controller.AuthController.authenticateUser(..))")
    public void logAfter(JoinPoint joinPoint) throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object[] signatureArgs = joinPoint.getArgs();

        for (Object signatureArg : signatureArgs) {
            Method getUsername = Class.forName(signatureArg.getClass().getName()).getDeclaredMethod("getUsername");
            logger.info(String.format("%s signed in successfully", getUsername.invoke(signatureArg)));
        }
    }
}
