package de.zambou.booki_booki.controller;
import de.zambou.booki_booki.dto.*;
import de.zambou.booki_booki.service.CustomerAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booki-booki/auth")
public class AuthController {
 private final CustomerAuthService auth;
 public AuthController(CustomerAuthService auth) { this.auth=auth; }
 @PostMapping("/register")
 public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
  return ResponseEntity.status(HttpStatus.CREATED).body(auth.register(request));
 }
 @PostMapping("/login")
 public AuthResponse login(@Valid @RequestBody LoginRequest request) { return auth.login(request); }
 @PostMapping("/logout")
 public ResponseEntity<Void> logout(@RequestHeader(value="Authorization",required=false) String authorization) {
  auth.logout(authorization); return ResponseEntity.noContent().build();
 }
}
