package com.gcxy.smartfarm;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.gcxy.smartfarm.mapper")
public class SmartFarmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartFarmServerApplication.class, args);
        log.info("server start!");
    }

}
