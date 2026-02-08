package com.codealpha.restaurant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.security.admin.username}")
    private String adminUsername;

    @Value("${app.security.admin.password}")
    private String adminPassword;

    @Value("${app.security.staff.username}")
    private String staffUsername;

    @Value("${app.security.staff.password}")
    private String staffPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager users(PasswordEncoder encoder) {

        UserDetails admin = User.withUsername(adminUsername)
                .password(encoder.encode(adminPassword))
                .roles("ADMIN")
                .build();

        UserDetails staff = User.withUsername(staffUsername)
                .password(encoder.encode(staffPassword))
                .roles("STAFF")
                .build();

        return new InMemoryUserDetailsManager(admin, staff);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.GET, "/api/menu", "/api/menu/**").permitAll()
                        .requestMatchers("/api/inventory/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
