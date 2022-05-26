package by.bsu.cookingportal.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/** @author Dmitry Kazakevich */
@Aspect
@Component
@Slf4j
public class LoggerAspect {
  @AfterThrowing(
      pointcut = "execution(* by.bsu.cookingportal.controller.impl.*.*(..))",
      throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
    Signature signature = joinPoint.getSignature();
    String methodName = signature.getName();
    String stuff = signature.toString();
    String arguments = Arrays.toString(joinPoint.getArgs());
    log.error(
        "Exception is caught in method: "
            + methodName
            + " with arguments "
            + arguments
            + "\nand the full toString: "
            + stuff
            + "\nthe exception is: "
            + e.getClass()
            + "\nthe exception message is: "
            + e.getMessage());

    throw e;
  }
}
