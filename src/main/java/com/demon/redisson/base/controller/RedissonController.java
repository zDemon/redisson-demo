package com.demon.redisson.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.demon.redisson.DistributeLock.DistributedLockCallback;
import com.demon.redisson.DistributeLock.SingleDistributedLockTemplate;
import com.demon.redisson.base.controller.model.User;
import com.google.common.collect.Maps;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Map;
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
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("hello")
    public String hello() throws UnsupportedEncodingException {
        String key = "redisson:user";
        User user;
        redisTemplate.opsForValue().set("foo", "test");
        Map<String, User> map = Maps.newHashMap();
        User mapUser = new User();
        mapUser.setName("测试hash");
        mapUser.setId(1001L);
        map.put("测试map", mapUser);
        redisTemplate.opsForValue().set("foo1", "22");
        redisTemplate.opsForValue().set("foo2", new String("中文啊哈哈".getBytes(), "UTF-8"));
        //if(redisTemplate.hasKey(key)) {
            user = (User) redisTemplate.opsForValue().get(key);
        /*} else {
            user = new User();
            user.setId(1000L);
            user.setName("我是测试A君");
            redisTemplate.opsForValue().set(key, user);
        }*/

        return (String) redisTemplate.opsForValue().get("foo2");

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
