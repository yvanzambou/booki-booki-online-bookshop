package de.zambou.booki_booki.dto;

public record AuthResponse(String token, Long id, String firstName, String lastName, String email) {
}
