package de.zambou.booki_booki.controller;

import de.zambou.booki_booki.dto.PaymentProfileRequest;
import de.zambou.booki_booki.model.Customer;
import de.zambou.booki_booki.model.PaymentProfile;
import de.zambou.booki_booki.repository.PaymentProfileRepository;
import de.zambou.booki_booki.service.CustomerAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booki-booki/account/payment-profile")
public class PaymentProfileController {
    private final CustomerAuthService auth;
    private final PaymentProfileRepository profiles;

    public PaymentProfileController(CustomerAuthService auth, PaymentProfileRepository profiles) {
        this.auth = auth;
        this.profiles = profiles;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<PaymentProfileRequest> get(@RequestHeader(value = "Authorization", required = false) String authorization) {
        Customer c = auth.requireCustomer(authorization);
        return profiles.findByCustomerId(c.getId())
                .map(p -> ResponseEntity.ok(new PaymentProfileRequest(p.getRecipient(), p.getStreet(), p.getPostalCode(), p.getCity(), p.getCountry(), p.getPaymentMethod(), p.getPaymentEmail())))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> save(@RequestHeader(value = "Authorization", required = false) String authorization,
                                     @Valid @RequestBody PaymentProfileRequest request) {
        Customer c = auth.requireCustomer(authorization);
        String method = request.paymentMethod();
        if (!java.util.Set.of("PAYPAL", "INVOICE", "CARD_PROVIDER").contains(method))
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Ungültige Zahlungsart.");
        PaymentProfile p = profiles.findByCustomerId(c.getId()).orElseGet(() -> new PaymentProfile(c));
        p.setRecipient(request.recipient().trim());
        p.setStreet(request.street().trim());
        p.setPostalCode(request.postalCode().trim());
        p.setCity(request.city().trim());
        p.setCountry(request.country().trim());
        p.setPaymentMethod(method);
        p.setPaymentEmail(request.paymentEmail() == null ? "" : request.paymentEmail().trim());
        profiles.save(p);
        return ResponseEntity.noContent().build();
    }
}
