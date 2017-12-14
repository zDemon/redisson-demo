package com.demon.redisson.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.demon.redisson.DistributeLock.DistributedLockCallback;
import com.demon.redisson.DistributeLock.SingleDistributedLockTemplate;
import com.demon.redisson.base.controller.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

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
    private RedisTemplate<String, User> redisTemplate;

    @GetMapping("hello")
    public String hello() {
        String key = "redisson:user";
        User user;
        redisTemplate.opsForValue().set("foo", new User());
        if(redisTemplate.hasKey(key)) {
            user = redisTemplate.opsForValue().get(key);
        } else {
            user = new User();
            user.setId(1000L);
            user.setName("我是测试A君");
            redisTemplate.opsForValue().set(key, user);
        }

        return JSONObject.toJSONString(user);

        /*String result = singleDistributedLockTemplate.lock(new DistributedLockCallback<String>() {
            @Override
            public String process() {
                return "result data";
            }

            @Override
            public String getLockName() {
                return "testName";
            }
        }, false);
        return result;*/
    }
}
