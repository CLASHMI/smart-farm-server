package com.gcxy.smartfarm.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Knife4jConfig 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 16:23
 * @since 2026/1/14
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("智慧养殖系统 API 接口文档")
                        .version("1.0")
                        .description("基于 Spring Boot 3 + Knife4j 生成的接口文档")
                        .contact(new Contact().name("后端开发组")));
    }
}
