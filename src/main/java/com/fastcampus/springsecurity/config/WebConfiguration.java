package com.fastcampus.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebConfiguration {
    @Autowired JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired JwtExcpetionFilter  jwtExcpetionFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000","http://127.0.0.1:3000")); // 허용 origin
        configuration.setAllowedMethods(List.of("GET","POST","PATCH","DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/**",configuration); // 적용하는 api 경로
        return source;

    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST,"/api/v1/users/**"
                        ,"PATTERN2"
                        ,"PATTERN3"
                        ,"pattern4")
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(
                        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // restapi 이므로 세션을 사용하지 않음
                .csrf(CsrfConfigurer::disable)// csrf 는 사용하지 않음
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExcpetionFilter, jwtAuthenticationFilter.getClass())
                .httpBasic(Customizer.withDefaults()); // basic Auth 사용 => 나중에 jwt 로 변경

        return http.build();

    }
}
