package com.gcxy.smartfarm.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcxy.smartfarm.common.constant.MessageConstant;
import com.gcxy.smartfarm.common.enums.ResultCode;
import com.gcxy.smartfarm.common.filter.JwtAuthenticationTokenFilter;
import com.gcxy.smartfarm.common.result.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationTokenFilter> preventGlobalRegistration(JwtAuthenticationTokenFilter filter) {
        FilterRegistrationBean<JwtAuthenticationTokenFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 1. 允许的来源，允许所有
        configuration.setAllowedOrigins(List.of("*")); // 偷懒写法：允许所有来源
        // 2. 允许的方法
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 3. 允许的头信息
        configuration.setAllowedHeaders(List.of("*"));
        // 4. 注册配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers(
                                "/doc.html",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        // (1) 未授权 / Token 问题 (100401)
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                            // 获取 Filter 中捕获的具体 JWT 异常
                            Object jwtError = request.getAttribute("JWT_ERROR");
                            Result<?> result;

                            // 统一使用 100401 作为业务状态码
                            Integer code = ResultCode.UNAUTHORIZED.getCode();

                            if (jwtError instanceof ExpiredJwtException) {
                                // Token 过期
                                result = Result.error(code, MessageConstant.Auth.TOKEN_EXPIRED);
                            } else if (jwtError instanceof JwtException) {
                                // Token 伪造/格式错
                                result = Result.error(code, MessageConstant.Auth.TOKEN_INVALID);
                            } else {
                                // 没带 Token
                                result = Result.error(code, MessageConstant.Auth.NOT_LOGIN);
                            }

                            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                        })
                        // (2) 权限不足 (100403)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                            Result<?> result = Result.error(ResultCode.FORBIDDEN);
                            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                        })
                )
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
