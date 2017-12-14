package com.demon.redisson.base.aop;

import com.demon.redisson.DistributeLock.DistributedLockTemplate;
import com.demon.redisson.base.annotation.DistributedLock;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * @Description:
 * @Auther Demon
 * @Date 2017/12/14 14:03 星期四
 */
@Aspect
@Component
public class DistributedLockAspect {

    @Autowired
    private DistributedLockTemplate distributedLockTemplate;

    // 定义切面
    @Pointcut("@annotation(com.demon.redisson.base.annotation.DistributedLock)")
    public void DistributedLockAspect() {}

    @Around(value = "DistributedLockAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getName();

        String methodName = joinPoint.getSignature().getName();

        Class targetClass = Class.forName(className);

        Method[] methods = targetClass.getMethods();

        Object[] arguments = joinPoint.getArgs();

        Optional<Method> optional = Arrays.stream(methods)
                .parallel()
                .filter(method -> method.getName().equals(methodName))
                .findAny();
        if(optional.isPresent()) {
            Method method = optional.get();
            //final String lockName =
        }

        return null;
    }

    private String getLockName(Method method, Object[] args) {
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        String lockName = distributedLock.lockName();
        String param = distributedLock.param();
        if(StringUtils.isBlank(lockName)) {
            if(StringUtils.isNotBlank(param) && args.length > 0) {
                Object arg = args[0];

            }
        } else {
            return lockName;
        }
        return null;
    }


}
