package de.zambou.booki_booki.repository;

import de.zambou.booki_booki.model.PaymentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentProfileRepository extends JpaRepository<PaymentProfile, Long> {
    Optional<PaymentProfile> findByCustomerId(Long customerId);
}
