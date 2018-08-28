package com.monsio.test.spring.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class MonitorePerf implements Ordered {

    private int order;

    @Around("monitorePerfPointcut()")
    public Object executer(final ProceedingJoinPoint joinpoint) throws Throwable {

        Object returnValue;

        StopWatch clock = new StopWatch(getClass().getName());

        try {

            log.info("début d'execution");

            clock.start(joinpoint.toString());

            returnValue = joinpoint.proceed();

        } finally {

            clock.stop();

            log.info("temps d'execution : " + clock.prettyPrint());
        }

        return returnValue;
    }

    @Override
    public int getOrder() {

        return this.order;
    }

    @Value("1")
    public void setOrder(final int order) {

        this.order = order;
    }

    @Pointcut("execution(* com.monsio.test.spring.aop.service.*ServiceImpl.*(..))")
    public void monitorePerfPointcut(){}


}