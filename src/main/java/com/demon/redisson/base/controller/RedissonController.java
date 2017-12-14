package com.demon.redisson.base.controller;

import com.demon.redisson.DistributeLock.DistributedLockCallback;
import com.demon.redisson.DistributeLock.SingleDistributedLockTemplate;
import org.redisson.api.RedissonClient;
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

    @GetMapping("hello")
    public String hello() {
        String result = singleDistributedLockTemplate.lock(new DistributedLockCallback<String>() {
            @Override
            public String process() {
                return "result data";
            }

            @Override
            public String getLockName() {
                return "testName";
            }
        }, false);
        return result;
    }

}
