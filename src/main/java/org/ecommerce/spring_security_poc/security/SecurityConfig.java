package org.ecommerce.spring_security_poc.security;

import org.ecommerce.spring_security_poc.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Usuario envia username e password
// Requisição chega até o filtro DelegatingFilterProxy
// Esse filtro faz a ponte entre o mundo do servlet e o mundo do Spring
// Esse filtro delega a requisição para a interface FilterChainProxy
// Essa interface mantém uma lista de SecurityFilterChain e delega a requisição para a cadeia apropriada
// Configuramos as urls em SecurityFilterChain
// Adicionamos um provider do tipo DaoAuthenticatorProvider
//

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/auth/**").permitAll();
            auth.requestMatchers("/public/**").permitAll();
            auth.anyRequest().authenticated();
        });
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(bananaFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BananaFilter bananaFilter() {
        return new BananaFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
