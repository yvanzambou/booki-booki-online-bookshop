package de.zambou.booki_booki.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartLineRequest(@NotNull Long bookId, @Min(1) int quantity) {
}
