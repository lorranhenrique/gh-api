package com.example.gh_api.config;

import com.example.gh_api.security.JwtService;
import com.example.gh_api.security.JwtAuthFilter;
import com.example.gh_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/v1/usuarios/**").permitAll()
                .requestMatchers(
                    "/api/v1/trabalhadores/**",
                    "/api/v1/cargos/**",
                    "/api/v1/camas/**").hasRole("ADMIN")
                .requestMatchers(
                    "/api/v1/hoteis/**",
                    "/api/v1/hospedes/**",
                    "/api/v1/reservas/**",
                    "/api/v1/itens/**",
                    "/api/v1/agendamentos/**",
                    "/api/v1/hospedagens/**",
                    "/api/v1/itensNoHotel/**",
                    "/api/v1/quartos/**",
                    "/api/v1/servicos/**",
                    "/api/v1/tiposDeCama/**",
                    "/api/v1/tiposDeQuarto/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(
                    "/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**"
                    ).permitAll()
                .anyRequest().permitAll()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
            

        return http.build();
    }
}
