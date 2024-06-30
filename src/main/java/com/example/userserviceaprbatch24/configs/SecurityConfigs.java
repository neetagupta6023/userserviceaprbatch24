package com.example.userserviceaprbatch24.configs;

import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigs {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DefaultSslBundleRegistry sslBundleRegistry) throws Exception {
        return http.authorizeHttpRequests(request -> {
            try {
                request.anyRequest()
                        .permitAll().and().cors().disable().csrf().disable();

            } catch (Exception ex) {

            }
        }).build();

    }
}
