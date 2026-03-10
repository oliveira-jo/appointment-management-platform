package com.devjoliveira.appointmentmanagementapi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.devjoliveira.appointmentmanagementapi.security.JwtTokenFilter;
import com.devjoliveira.appointmentmanagementapi.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserDetailsServiceImpl userDetailsService;

  private final JwtTokenFilter jwtTokenFilter;

  public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtTokenFilter jwtTokenFilter) {
    this.userDetailsService = userDetailsService;
    this.jwtTokenFilter = jwtTokenFilter;

  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .authenticationProvider(authenticationProvider())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/auth/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/professionals").permitAll()
            .requestMatchers(HttpMethod.GET, "/products").permitAll()
            .requestMatchers(HttpMethod.GET, "/customers").permitAll()
            .requestMatchers(HttpMethod.GET, "/appointments").permitAll()
            .anyRequest().authenticated())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .formLogin(login -> login.disable())
        .logout(logout -> logout.disable());

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.applyPermitDefaultValues();

    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}