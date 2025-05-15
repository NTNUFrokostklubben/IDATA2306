package no.ntnu.learniverseconnect.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Creates AuthenticationManager - set up authentication type.
 * The @EnableMethodSecurity is needed so that each endpoint can specify which role it requires
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private JwtRequestFilter jwtRequestFilter;
  final static String admin = "ROLE_ADMIN";
  final static String user = "ROLE_USER";


  /**
   * This method will be called automatically by the framework to find the authentication to use.
   *
   * @param http HttpSecurity setting builder
   * @throws Exception When security configuration fails
   */
  @Bean
  public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
    http
        // Enable CORS with custom configuration
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers(HttpMethod.POST,  "/authenticate", "/signup").permitAll()
            .requestMatchers(HttpMethod.GET, "/course/**", "/courses/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/providers/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/userCourses/reviews/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/userCourses/averageRating/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/offerableCourses/**").permitAll()
            .requestMatchers( HttpMethod.GET, "/keyword/**").permitAll()

            .requestMatchers("/userDto/**").hasAnyAuthority(user, admin)
            .requestMatchers(HttpMethod.POST, "/transaction/**").hasAnyAuthority(user, admin)

            .requestMatchers("/**").hasAuthority( admin)



        )
        .sessionManagement((session) ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * This method is called to decide what encryption to use for password checking.
   *
   * @return The password encryptor
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(
        "https://localhost:3000",
        "http://localhost:3000",
        "https://learniverse.no",
        "https://www.learniverse.no"
    ));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
