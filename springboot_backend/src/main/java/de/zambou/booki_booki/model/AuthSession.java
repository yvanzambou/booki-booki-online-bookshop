package de.zambou.booki_booki.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "auth_sessions")
public class AuthSession {
    @Id
    @Column(length = 36)
    private String token;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;
    @Column(nullable = false)
    private Instant expiresAt;

    protected AuthSession() {}
    public AuthSession(String token, Customer customer, Instant expiresAt) {
        this.token = token; this.customer = customer; this.expiresAt = expiresAt;
    }
    public String getToken() { return token; }
    public Customer getCustomer() { return customer; }
    public Instant getExpiresAt() { return expiresAt; }
}
