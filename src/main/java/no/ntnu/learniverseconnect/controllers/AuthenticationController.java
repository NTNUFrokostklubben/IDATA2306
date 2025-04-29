package no.ntnu.learniverseconnect.controllers;

import no.ntnu.learniverseconnect.model.dto.UserDto;
import no.ntnu.learniverseconnect.security.AccessUserService;
import no.ntnu.learniverseconnect.model.dto.AuthenticationRequest;
import no.ntnu.learniverseconnect.model.dto.AuthenticationResponse;
import no.ntnu.learniverseconnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;


@RestController
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AccessUserService userService;

  @Autowired
  private JwtUtil jwtUtil;


  /**
   * HTTP POST request to /authenticate
   *
   * @param authenticationRequest The request JSON object containing username and password
   * @return  OK + JWT token; Or UNAUTHORIZED
   */
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
    try{
      System.out.println("Username "+authenticationRequest.getUsername());
      System.out.println("Password "+authenticationRequest.getPassword());
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getUsername(),
          authenticationRequest.getPassword()));
    } catch (BadCredentialsException e){
      System.out.println(e.getMessage());
      return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    final UserDetails userDetails = userService.loadUserByUsername(
        authenticationRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }


  @PostMapping("/signup")
  public ResponseEntity<String> signupProcess(@RequestBody UserDto userDto){
    ResponseEntity<String> response;
    try{
      userService.tryCreateNewUser(userDto.getName(), userDto.getPasswordHash());
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e){
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

}
