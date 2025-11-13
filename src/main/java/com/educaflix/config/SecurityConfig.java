package com.educaflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Classe de configuração de segurança para a aplicação.
 * <p>
 * Utiliza o Spring Security para definir regras de acesso e desabilitar autenticações padrão.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura o filtro de segurança principal da aplicação.
     *
     * <ul>
     *   <li>Desativa CSRF, login por formulário, autenticação HTTP Basic e logout automático.</li>
     *   <li>Permite acesso público às rotas principais, console H2 e recursos estáticos.</li>
     *   <li>Desabilita proteção de frame para permitir o uso do console H2.</li>
     * </ul>
     *
     * @param http objeto de configuração HTTP do Spring Security
     * @return instância configurada de {@link SecurityFilterChain}
     * @throws Exception caso ocorra erro na configuração
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/cadastro",
                                "/h2-console/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
