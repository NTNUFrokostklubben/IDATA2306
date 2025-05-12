package no.ntnu.learniverseconnect.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import no.ntnu.learniverseconnect.model.dto.AuthenticationRequest;
import no.ntnu.learniverseconnect.model.dto.AuthenticationResponse;
import no.ntnu.learniverseconnect.model.dto.UserDto;
import no.ntnu.learniverseconnect.security.AccessUserService;
import no.ntnu.learniverseconnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The authentication's controller.
 */
@RestController
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AccessUserService userService;

  @Autowired
  private JwtUtil jwtUtil;

@Operation(
    summary = "Authenticate user",
    description = "Authenticates a user and returns a JWT token")
@ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Authentication successful"
    ),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Invalid username or password"
    )
})
@io.swagger.v3.oas.annotations.parameters.RequestBody(
    description = "Authentication request containing email and password",
    required = true,
    content = @io.swagger.v3.oas.annotations.media.Content(
        schema = @io.swagger.v3.oas.annotations.media.Schema(
            implementation = AuthenticationRequest.class
        )
    ))
  /**
   * HTTP POST request to /authenticate.
   *
   * @param authenticationRequest The request JSON object containing username and password
   * @return OK + JWT token; Or UNAUTHORIZED
   */
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getEmail(),
          authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
    final UserDetails userDetails = userService.loadUserByUsername(
        authenticationRequest.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }


  /**
   * Sign up the user based on email, username and password in the user dto.
   *
   * @param userDto the dto with email, username and password
   * @return OK + JWT token; Or UNAUTHORIZED
   */
    @Operation(
        summary = "Sign up user",
        description = "Creates a new user and returns a JWT token")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "User created successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid input"
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Signup request containing email, name and password hash",
        required = true,
        content = @io.swagger.v3.oas.annotations.media.Content(
            schema = @io.swagger.v3.oas.annotations.media.Schema(
                implementation = UserDto.class
            )
    ))
  @PostMapping("/signup")
  public ResponseEntity<?> signupProcess(@RequestBody UserDto userDto) {
    ResponseEntity<?> response;
    try {
      userService.tryCreateNewUser(userDto.getName(), userDto.getPasswordHash(),
                                   userDto.getEmail());
      response = authenticate(
          new AuthenticationRequest(userDto.getEmail(), userDto.getPasswordHash()));
    } catch (IOException e) {
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

}
