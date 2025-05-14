package no.ntnu.learniverseconnect.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * A filter that is applied to all HTTP requests and checks for a valid JWT token in
 * the `Authorization: Bearer ...` header.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(
      JwtRequestFilter.class.getSimpleName());

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  /**
   * Strip the "Bearer " prefix from the Header "Authorization: Bearer ...
   *
   * @param authorizationHeaderValue The value of the Authorization HTTP header
   * @return The JWT token following the "Bearer " prefix
   */
  private static String stripBearerPrefixFrom(String authorizationHeaderValue) {
    final int numberOfCharsToStrip = "Bearer ".length();
    return authorizationHeaderValue.substring(numberOfCharsToStrip);
  }

  /**
   * Checks if they are authenticated yet.
   *
   * @return if they are authenticated yet
   */
  private static boolean notAuthenticatedYet() {
    return SecurityContextHolder.getContext().getAuthentication() == null;
  }

  /**
   * Register the user as authenticated.
   *
   * @param request     the request
   * @param userDetails the user details
   */
  private void registerUserAsAuthenticated(HttpServletRequest request,
                                           String jwtToken,
                                           String username) {
    // Extract roles from JWT
    var claims = jwtUtil.extractAllClaims(jwtToken);
    List<Map<String, String>> roles = claims.get("roles", List.class);

    List<SimpleGrantedAuthority> authorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.get("authority")))
        .toList();  // Java 16+ or use .collect(Collectors.toList()) for earlier versions

    // Create authentication token
    UsernamePasswordAuthenticationToken upat =
        new UsernamePasswordAuthenticationToken(username, null, authorities);

    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(upat);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {
    String jwtToken = getJwtToken(request);
    String username = jwtToken != null ? getEmailFrom(jwtToken) : null;

    if (username != null && notAuthenticatedYet()) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtUtil.validateToken(jwtToken, userDetails )) {
        Claims claims = jwtUtil.extractAllClaims(jwtToken);

        List<Map<String, String>> roles = claims.get("roles", List.class);
        List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.get("authority")))
            .toList();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, authorities);

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println("Extracted authorities from JWT: " + authorities);
      }
    }
    System.out.println("SECURITY CONTEXT AUTH: " + SecurityContextHolder.getContext().getAuthentication());
    filterChain.doFilter(request, response);
  }

  /**
   * Retrieves the user details from the database.
   *
   * @param email the identifying email of the user
   * @return the user details of that user
   */
  private UserDetails getUserDetailsFromDatabase(String email) {
    UserDetails userDetails = null;
    try {
      userDetails = userDetailsService.loadUserByUsername(email);
    } catch (UsernameNotFoundException e) {
      logger.warn("User " + email + " not found in the database");
    }
    return userDetails;
  }

  /**
   * Retrieves the JWT token.
   *
   * @param request the http servlet request
   * @return tje JWT token
   */
  private String getJwtToken(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("Authorization");
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = stripBearerPrefixFrom(authorizationHeader);
    }
    return jwt;
  }

  /**
   * Get the email from the jwt token.
   *
   * @param jwtToken the jwt token
   * @return the email
   */
  private String getEmailFrom(String jwtToken) {
    String username = null;
    try {
      username = jwtUtil.extractEmail(jwtToken);
    } catch (MalformedJwtException e) {
      logger.warn("Malformed JWT: " + e.getMessage());
    } catch (JwtException e) {
      logger.warn("Error in the JWT token: " + e.getMessage());
    }
    return username;
  }
}
