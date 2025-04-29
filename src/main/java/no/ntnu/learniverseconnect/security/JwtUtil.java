package no.ntnu.learniverseconnect.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling JWT tokens
 */
@Component
public class JwtUtil {

  @Value("${jwt.secret.key}")
  private String secretKey;

  /**
   * Key inside JWT token where roles are stored.
   */
  private static final String ROLE_KEY = "roles";

  /**
   * Generate a JWT token for an authenticated user.
   *
   * @param userDetails Object containing user details
   * @return JWT token string
   */
  public String generateToken(UserDetails userDetails){
    final long timeNow = System.currentTimeMillis();
    final long timeAfterOneHour = timeNow + (60*60*100) ;

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim(ROLE_KEY, userDetails.getAuthorities())
        .issuedAt(new Date(timeNow))
        .expiration(new Date(timeAfterOneHour))
        .signWith(getSigningKey())
        .compact();
  }

  /**
   * Gets the signing key for generating the JWT token.
   *
   * @return the signing key for the JWT token
   */
  private SecretKey getSigningKey(){
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA226");
  }

  /**
   * Extract the username based on JWT token
   *
   * @param token JWT token
   * @return Username
   */
  public String extractUsername(String token) throws JwtException{
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts the claim based on token.
   *
   * @param token the token
   * @param claimsResolver the claims
   * @return claims
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from token.
   *
   * @param token the token
   * @return claims
   */
  private Claims extractAllClaims(String token){
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Checks if the token is expired.
   *
   * @param token the token to check
   * @return whether the token is expired or not
   */
  private Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from the token.
   *
   * @param token the token to extract the date from
   * @return the expiration date of the token
   */
  private Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Checks if the token is valid based on userDetails.
   *
   * @param token the token that gets checked
   * @param userDetails the user details
   * @return whether the token is valid or not
   * @throws JwtException
   */
  public boolean validateToken(String token, UserDetails userDetails) throws JwtException{
    final String username = extractUsername(token);
    Boolean isValid = (userDetails != null && username.equals(userDetails.getUsername())
                      && !isTokenExpired(token));
    return isValid;
  }



}
