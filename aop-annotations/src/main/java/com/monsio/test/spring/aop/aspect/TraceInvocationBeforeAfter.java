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
public class TraceInvocationBeforeAfter implements Ordered {

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

    @Before("traceInvocationBeforeAfter()")
    public void afficherDebutTrace(final JoinPoint joinpoint) throws Throwable {

        String nomMethode = joinpoint.getTarget().getClass().getSimpleName()
                + "." + joinpoint.getSignature().getName();

        StringBuffer sb = addLog(joinpoint);

        log.info("--------------BEFORE----------");

        log.info("Debut methode : " + nomMethode + " " + sb);
    }


    @AfterReturning(value = "traceInvocationBeforeAfter()",returning = "result")
    public void afficherFinReturnTrace(final JoinPoint.StaticPart staticPart, final Object result) throws Throwable {

        String nomMethode = staticPart.getSignature().toLongString();

        log.info("-------------AFTER RETURN-------------");

        log.info("Fin methode :  " + nomMethode + " retour=" + result);
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
    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.find(..))")
    public void traceInvocationBeforeAfter(){}

}