package de.zambou.booki_booki.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PaymentProfileRequest(
        @NotBlank @Size(max = 120) String recipient,
        @NotBlank @Size(max = 180) String street,
        @NotBlank @Size(max = 20) String postalCode,
        @NotBlank @Size(max = 100) String city,
        @NotBlank @Size(max = 80) String country,
        @NotBlank String paymentMethod,
        @Size(max = 120) String paymentEmail
) {
}
