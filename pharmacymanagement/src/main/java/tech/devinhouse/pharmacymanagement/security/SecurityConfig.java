package tech.devinhouse.pharmacymanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.devinhouse.pharmacymanagement.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    JwtAuthenticationFilter filter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()


                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeHttpRequests((auth) -> {
                                auth
                                        .requestMatchers("/usuarios/login").permitAll()

                                        .requestMatchers(HttpMethod.GET)
                                        .hasAnyRole("ADMIN", "GERENTE", "COLABORADOR")

                                        .requestMatchers(HttpMethod.POST)
                                        .hasAnyRole("ADMIN", "GERENTE", "COLABORADOR")

                                        .requestMatchers(HttpMethod.PUT)
                                        .hasAnyRole("ADMIN", "GERENTE")

                                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");
                        }
                );
        http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

}
