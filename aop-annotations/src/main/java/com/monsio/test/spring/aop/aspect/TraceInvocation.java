package com.monsio.test.spring.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TraceInvocation implements Ordered {

    private int order;

    private StringBuffer addLog(final JoinPoint joinpoint){

        final Object[] args = joinpoint.getArgs();

        final StringBuffer sb = new StringBuffer();

        sb.append(joinpoint.getSignature().toString());

        sb.append(" avec les parametres : (");

        for (int i = 0; i < args.length; i++) {

            sb.append(args[i]);

            if (i < args.length - 1) {

                sb.append(", ");
            }
        }

        sb.append(")");

        return sb;
    }

    @Around("traceInvocationAround()")
    public Object afficherAroundTrace(final ProceedingJoinPoint joinpoint) throws Throwable {

        String nomMethode = joinpoint.getTarget().getClass().getSimpleName()
                + "." + joinpoint.getSignature().getName();

        StringBuffer sb = addLog(joinpoint);

        log.info("--------------AROUND----------");

        log.info("Debut methode : " + sb);

        Object obj = null;

        try {

            obj = joinpoint.proceed();

        } finally {

            log.info("Fin methode : " + nomMethode + " - retour = " + obj);
        }

        return obj;
    }

    @Before("traceInvocationBefore()")
    public void afficherDebutTrace(final JoinPoint joinpoint) throws Throwable {

        String nomMethode = joinpoint.getTarget().getClass().getSimpleName()
                + "." + joinpoint.getSignature().getName();

        StringBuffer sb = addLog(joinpoint);

        log.info("--------------BEFORE----------");

        log.info("Debut methode : " + nomMethode + " " + sb);
    }

    @After("traceInvocationAfter()")
    public void afficherFinTrace(final JoinPoint joinpoint) throws Throwable {

        String nomMethode = joinpoint.getTarget().getClass().getSimpleName()
                + "." + joinpoint.getSignature().getName();

        StringBuffer sb = addLog(joinpoint);

        log.info("-------------AFTER-------------");

        log.info("Fin methode : " + nomMethode + " "+ sb);
    }

    @AfterReturning(value = "traceInvocationAfterReturn()",returning = "result")
    public void afficherFinReturnTrace(final JoinPoint.StaticPart staticPart, final Object result) throws Throwable {

        String nomMethode = staticPart.getSignature().toLongString();

        log.info("-------------AFTER RETURN-------------");

        log.info("Fin methode :  " + nomMethode + " retour=" + result);
    }

    @AfterThrowing(value = "traceInvocationAfterReturnException()",throwing = "exception")
    public void afficherExceptionTrace(final JoinPoint.StaticPart staticPart, final Exception exception) throws Throwable {

        String nomMethode = staticPart.getSignature().toLongString();

        log.info("-------------AFTER RETURN EXCEPTION-------------");

        log.error("Exception durant la methode :  " + nomMethode, exception);

        throw new MyException("Exception encapsulé - message d'origine : "+exception.getMessage(), exception);
    }

    public static class MyException extends Exception {

        public MyException(String message, Throwable cause) {

            super(message, cause);
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Value("2")
    public void setOrder(int order) {
        this.order = order;
    }

    //POINTCUTS

    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.update(..))")
    public void traceInvocationAround(){}

    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.find(..))")
    public void traceInvocationBeforeAfter(){}

    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.show(..))")
    public void traceInvocationBefore(){}

    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.get(..))")
    public void traceInvocationAfter(){}

    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.add(..))")
    public void traceInvocationAfterReturn(){}

    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.delete(..))")
    public void traceInvocationAfterReturnException(){}

}