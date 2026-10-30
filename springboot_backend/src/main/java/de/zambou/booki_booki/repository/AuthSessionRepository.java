package de.zambou.booki_booki.repository;

import de.zambou.booki_booki.model.AuthSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface AuthSessionRepository extends JpaRepository<AuthSession, String> {
    Optional<AuthSession> findByTokenAndExpiresAtAfter(String token, Instant now);
}
