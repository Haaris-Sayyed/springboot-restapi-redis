package com.api.redis.redis_demo;

import org.springframework.boot.SpringApplication;

public class TestRedisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(RedisDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
