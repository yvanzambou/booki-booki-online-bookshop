package de.zambou.booki_booki.controller;

import de.zambou.booki_booki.dto.CartLineRequest;
import de.zambou.booki_booki.dto.CartLineResponse;
import de.zambou.booki_booki.model.Customer;
import de.zambou.booki_booki.service.CustomerAuthService;
import de.zambou.booki_booki.service.CustomerCartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booki-booki/account/cart")
public class CustomerCartController {
    private final CustomerAuthService authService;
    private final CustomerCartService cartService;

    public CustomerCartController(CustomerAuthService authService, CustomerCartService cartService) {
        this.authService = authService;
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartLineResponse> getCart(@RequestHeader(value = "Authorization", required = false) String authorization) {
        Customer customer = authService.requireCustomer(authorization);
        return cartService.getCart(customer);
    }

    @PutMapping
    public List<CartLineResponse> replaceCart(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody List<CartLineRequest> lines) {
        Customer customer = authService.requireCustomer(authorization);
        return cartService.replaceCart(customer, lines);
    }
}
