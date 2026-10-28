package de.zambou.booki_booki.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}
