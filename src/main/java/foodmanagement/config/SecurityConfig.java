package foodmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST API
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/admins/generate-code/**").permitAll()
                .requestMatchers("/admins/verify-code").permitAll()
                .requestMatchers("/admins/register").permitAll()
                .requestMatchers("/admins/login").permitAll() // ✅ allow login
                .requestMatchers("/customers/**").permitAll()
                // Any other requests require authentication
                .anyRequest().authenticated()
            )
            .httpBasic().disable() // Optional
            .formLogin().disable()
            .logout().disable();

        return http.build();
    }
}
