package foodmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        // Allow your frontend origins
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:9090",
            "http://localhost:5173"
        ));

        // OR for dev/testing: 
        // config.addAllowedOriginPattern("*");

        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","PATCH"));

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
