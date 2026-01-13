package com.gcxy.smartfarm.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcxy.smartfarm.common.constant.MessageConstant;
import com.gcxy.smartfarm.common.filter.JwtAuthenticationTokenFilter;
import com.gcxy.smartfarm.common.result.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 15:04
 * @since 2026/1/13
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Spring Security 6 推荐注解
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 禁用 CSRF 和 Logout (使用 Lambda 写法)
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                // 2. 允许跨域 (需配合 CorsConfig)
                .cors(Customizer.withDefaults())

                // 3. 禁用 Session (无状态模式)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. 接口权限配置 (Spring Security 6 写法)
                .authorizeHttpRequests(auth -> auth
                        // 放行登录接口
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        // 放行静态资源和 Swagger 文档
                        .requestMatchers(
                                "/doc.html",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**" // Boot 3 通常用 v3
                        ).permitAll()
                        // 其他所有请求都需要认证
                        .anyRequest().authenticated()
                )

                // 5. 异常处理
                .exceptionHandling(exceptions -> exceptions
                        // (1) 未登录或 Token 无效的处理
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK); // 保持200状态码，由前端判断 code

                            // 获取 Filter 中设置的异常信息
                            Object jwtError = request.getAttribute("JWT_ERROR");
                            // 修正：引用字符串常量或直接写死提示，防止 MessageConstant 未定义错误
                            Result<?> result = Result.error(MessageConstant.NOT_LOGIN);

                            if (jwtError instanceof ExpiredJwtException) {
                                result = Result.error("登录凭证已过期，请重新登录");
                            } else if (jwtError instanceof JwtException) {
                                result = Result.error("登录凭证无效");
                            }

                            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                        })
                        // (2) 权限不足的处理
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK);
                            Result<?> result = Result.error(MessageConstant.PERMISSION_DENIED);
                            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                        })
                )

                // 6. 添加 JWT 过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
