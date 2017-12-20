package com.demon.redisson.controller;

import com.demon.redisson.DistributeLock.SingleDistributedLockTemplate;
import com.demon.redisson.service.LockService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Auther Demon
 * @Date 2017/12/14 11:15 星期四
 */
@RestController
@RequestMapping("redisson")
public class RedissonController {

    @Resource
    SingleDistributedLockTemplate singleDistributedLockTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private LockService lockService;

    @GetMapping("test")
    public String test() throws InterruptedException {
        for(int i=0; i< 10; i++) {
            Thread.sleep(1000L);
            new Thread(new Test()).start();
        }
        return "Done!";
    }

    class Test implements Runnable {

        @Override
        public void run() {
            try {
                lockService.doTest();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
