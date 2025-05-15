package no.ntnu.learniverseconnect.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Server configuration.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {

    String[] allowedOrigins = {
        "http://localhost:3000",
        "https://learniverse.no",
        "https://www.learniverse.no"
        };

    registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods("GET", "PUT", "DELETE", "POST", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
  }

}
