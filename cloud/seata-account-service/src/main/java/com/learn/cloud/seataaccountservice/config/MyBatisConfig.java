package com.learn.cloud.seataaccountservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.learn.cloud.*.dao"})
public class MyBatisConfig {
}
