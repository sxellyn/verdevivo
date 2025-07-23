package com.verdevivo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private AuthEntryPoint unauthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {}) // enable CORS
            .csrf(csrf -> csrf.disable()) // disable csrf 
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler)) //unauthorized attempt handler
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //disable http session using stateless api
            .authorizeHttpRequests(
            auth -> auth //says which endpoints are private/public:
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/users").permitAll() // 🔓 permite somente POST em /users
            .requestMatchers("/index.html", "/dashboard.html", "/css/**", "/js/**").permitAll() //permited to access interface
            .anyRequest().authenticated()
            )
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class); //add custom jwt before original one

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

        // cors configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080")); // switch for especific origin, allows any origin.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); //allowed methods
        configuration.setAllowedHeaders(List.of("*")); //allowed headers
        configuration.setAllowCredentials(true); //allow sending cookies and auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //cors config applies to every route/ED
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
