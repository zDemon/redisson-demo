package com.demon.redisson.base.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 分布式锁注解，加载需要加锁的方法上
 * @Auther Demon
 * @Date 2017/12/14 11:11 星期四
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    String lockName() default "";

    String lockNamePre() default ""; //lockName后缀

    String lockNamePost() default ""; //lockName后缀

    String param() default ""; //获取注解的方法第一个参数对象的某个属性值来作为lockName。因为有时候lockName是不固定的。

    boolean fairLock() default false;  //是否使用公平锁。

    boolean tryLock() default false;  //是否使用尝试锁。

    long waitTime() default 30L;

    long leaseTime() default 5L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;


}
