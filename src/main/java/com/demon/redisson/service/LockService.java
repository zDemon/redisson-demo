package com.demon.redisson.service;

import com.demon.redisson.base.annotation.DistributedLock;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther Demon
 * @Date 2017/12/20 18:32 星期三
 */
@Service
public class LockService {

    static int NUMBER = 10;

    @DistributedLock(lockName = "test", fairLock = true)
    public void doTest() throws InterruptedException {
        if(NUMBER>=9)
        Thread.sleep(11000L);
        System.out.println(Thread.currentThread().getName() + " :" + --NUMBER);
    }

}
