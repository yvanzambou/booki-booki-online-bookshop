package de.zambou.booki_booki.service;

import de.zambou.booki_booki.dto.*;
import de.zambou.booki_booki.model.*;
import de.zambou.booki_booki.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class CustomerAuthService {
 private final CustomerRepository customers;
 private final AuthSessionRepository sessions;
 private final PasswordEncoder encoder;
 public CustomerAuthService(CustomerRepository customers, AuthSessionRepository sessions, PasswordEncoder encoder) {
  this.customers=customers; this.sessions=sessions; this.encoder=encoder;
 }
 @Transactional
 public AuthResponse register(RegisterRequest r) {
  String email=r.email().trim().toLowerCase();
  if(customers.existsByEmailIgnoreCase(email)) throw new ResponseStatusException(HttpStatus.CONFLICT,"Diese E-Mail-Adresse ist bereits registriert.");
  Customer c=new Customer(); c.setEmail(email); c.setFirstName(r.firstName().trim()); c.setLastName(r.lastName().trim()); c.setPasswordHash(encoder.encode(r.password()));
  customers.save(c); return createSession(c);
 }
 @Transactional
 public AuthResponse login(LoginRequest r) {
  Customer c=customers.findByEmailIgnoreCase(r.email().trim()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"E-Mail oder Passwort ist falsch."));
  if(!encoder.matches(r.password(),c.getPasswordHash())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"E-Mail oder Passwort ist falsch.");
  return createSession(c);
 }
 private AuthResponse createSession(Customer c) {
  String token=UUID.randomUUID().toString();
  sessions.save(new AuthSession(token,c,Instant.now().plus(7,ChronoUnit.DAYS)));
  return new AuthResponse(token,c.getId(),c.getFirstName(),c.getLastName(),c.getEmail());
 }
 @Transactional(readOnly=true)
 public Customer requireCustomer(String authorization) {
  if(authorization==null || !authorization.startsWith("Bearer ")) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Bitte melde dich an.");
  String token=authorization.substring(7).trim();
  return sessions.findByTokenAndExpiresAtAfter(token,Instant.now()).map(AuthSession::getCustomer)
   .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Sitzung abgelaufen. Bitte melde dich erneut an."));
 }
 @Transactional
 public void logout(String authorization) {
  if(authorization!=null && authorization.startsWith("Bearer ")) sessions.deleteById(authorization.substring(7).trim());
 }
}
