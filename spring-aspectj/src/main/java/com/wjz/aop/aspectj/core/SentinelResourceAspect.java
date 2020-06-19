package com.wjz.aop.aspectj.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

@Aspect
public class SentinelResourceAspect extends AbstractSentinelAspectSupport {

    @Pointcut("@annotation(com.wjz.aop.aspectj.core.SentinelResource)")
    public void sentinelResourceAnnotationPointcut() {
    }

    @Around("sentinelResourceAnnotationPointcut()")
    public Object invokeResourceWithSentinel(ProceedingJoinPoint pjp) throws Throwable {
        Method originMethod = resolveMethod(pjp);
        SentinelResource annotation = originMethod.getAnnotation(SentinelResource.class);
        if (annotation == null) {
            // Should not go through here.
            throw new IllegalStateException("Wrong state for SentinelResource annotation");
        }
        try {
            System.out.println("执行目标方法之前前置处理");
            return pjp.proceed();
        } catch (BlockException e) {
            System.out.println("执行目标方法时被降级");
            return handleBlockException(pjp, annotation, e);
        } catch (Throwable e) {
            System.out.println("执行目标方法时出现异常");
            throw e;
        }
    }
}
