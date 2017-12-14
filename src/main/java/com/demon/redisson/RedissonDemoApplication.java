package com.demon.redisson;

import com.demon.redisson.DistributeLock.DistributedLockTemplate;
import com.demon.redisson.DistributeLock.SingleDistributedLockTemplate;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.IOException;

@SpringBootApplication
public class RedissonDemoApplication {

	@Value("classpath:/redisson-conf.yml")
	Resource configFile;

	public static void main(String[] args) {
		SpringApplication.run(RedissonDemoApplication.class, args);
	}


	@Bean(destroyMethod = "shutdown")
	RedissonClient redisson() throws IOException {
		Config config = Config.fromYAML(configFile.getInputStream());
		return Redisson.create(config);
	}

	@Bean
	DistributedLockTemplate distributedLockTemplate(RedissonClient redissonClient) {
		return new SingleDistributedLockTemplate(redissonClient);
	}
}
