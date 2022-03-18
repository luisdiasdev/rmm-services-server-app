package com.luisdias.rmmservice.infra.security;

import com.luisdias.rmmservice.modules.shared.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.luisdias.rmmservice.infra.security.SecurityConstants.AUTH_LOGIN_URL;
import static com.luisdias.rmmservice.infra.security.SecurityConstants.CREATE_CUSTOMER_URL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenParser jwtTokenParser;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(JwtTokenParser jwtTokenParser,
                          UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtTokenParser = jwtTokenParser;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var publicEndpoints = new String[]{
                AUTH_LOGIN_URL,
                "/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        };

        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(publicEndpoints).permitAll()
                .antMatchers(HttpMethod.POST, CREATE_CUSTOMER_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenParser), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE"));
        config.setAllowedHeaders(
                List.of("Accept", "Authorization", "Origin", "X-Requested-With", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
