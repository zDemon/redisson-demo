package com.demon.redisson.base.aop;

import com.demon.redisson.DistributeLock.DistributedLockCallback;
import com.demon.redisson.DistributeLock.DistributedLockTemplate;
import com.demon.redisson.base.annotation.DistributedLock;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        //System.out.print(Thread.currentThread().getName() + "\t");
        Class<?> clazz = joinPoint.getTarget().getClass();

        String methodName = joinPoint.getSignature().getName();

        //得到方法的参数的类型
        Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();

        Method method = clazz.getMethod(methodName, parameterTypes);
        final String lockName = getLockName(method, joinPoint.getArgs());
        return lock(joinPoint, method, lockName);

    }

    private String getLockName(Method method, Object[] args) throws Throwable {
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        String lockName = distributedLock.lockName();
        String param = distributedLock.param();
        if(StringUtils.isBlank(lockName)) {
            if(StringUtils.isNotBlank(param) && args.length > 0) {
                Object arg = args[0];
                lockName = String.valueOf(getParam(arg, param));
                String preLockName = distributedLock.lockNamePre();
                String postLockName = distributedLock.lockNamePost();

                lockName = preLockName + lockName + postLockName;

                return lockName;
            }
        } else {
            return lockName;
        }
        throw new IllegalArgumentException("lockName can't be empty!");
    }

    /**
     * 从方法参数获取数据
     *
     * @param param
     * @param arg 方法的参数数组
     * @return
     */
    public Object getParam(Object arg, String param) throws Throwable {

        if (!StringUtils.isEmpty(param) && arg != null) {
            Object result = PropertyUtils.getProperty(arg, param);
            return result;
        }
        return null;
    }

    public Object lock(ProceedingJoinPoint pjp, Method method, final String lockName) {

        DistributedLock annotation = method.getAnnotation(DistributedLock.class);

        boolean fairLock = annotation.fairLock();

        boolean tryLock = annotation.tryLock();

        if (tryLock) {
            return tryLock(pjp, annotation, lockName, fairLock);
        } else {
            return lock(pjp,lockName, fairLock);
        }
    }


    public Object lock(ProceedingJoinPoint pjp, final String lockName, boolean fairLock) {

        return distributedLockTemplate.lock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, fairLock);
    }

    public Object tryLock(ProceedingJoinPoint pjp, DistributedLock annotation, final String lockName, boolean fairLock) {
        long waitTime = annotation.waitTime();
        long leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();

        return distributedLockTemplate.tryLock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, waitTime, leaseTime, timeUnit, fairLock);
    }

    public Object proceed(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


}
