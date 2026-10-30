package de.zambou.booki_booki.service;

import de.zambou.booki_booki.dto.CartLineRequest;
import de.zambou.booki_booki.dto.CartLineResponse;
import de.zambou.booki_booki.mapper.BookMapper;
import de.zambou.booki_booki.model.Book;
import de.zambou.booki_booki.model.CartItem;
import de.zambou.booki_booki.model.Customer;
import de.zambou.booki_booki.repository.BookRepository;
import de.zambou.booki_booki.repository.CartItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerCartService {

    private final CartItemRepository cartItems;
    private final BookRepository books;

    public CustomerCartService(
            CartItemRepository cartItems,
            BookRepository books) {
        this.cartItems = cartItems;
        this.books = books;
    }

    @Transactional(readOnly = true)
    public List<CartLineResponse> getCart(Customer customer) {
        return cartItems.findByCustomerIdOrderByIdAsc(customer.getId())
                .stream()
                .map(item -> new CartLineResponse(
                        BookMapper.toDTO(item.getBook()),
                        item.getQuantity()))
                .toList();
    }

    @Transactional
    public List<CartLineResponse> replaceCart(
            Customer customer,
            List<CartLineRequest> lines) {

        // 1. Doppelte Buch-IDs zusammenführen.
        Map<Long, Integer> quantitiesByBookId = new LinkedHashMap<>();

        if (lines != null) {
            for (CartLineRequest line : lines) {
                if (line == null
                        || line.bookId() == null
                        || line.quantity() < 1) {
                    continue;
                }

                quantitiesByBookId.merge(
                        line.bookId(),
                        line.quantity(),
                        Integer::sum);
            }
        }

        // 2. Bisherigen Warenkorb löschen.
        cartItems.deleteByCustomerId(customer.getId());

        // 3. DELETE vor den neuen INSERTs ausführen.
        cartItems.flush();

        // 4. Jede Buch-ID nur einmal speichern.
        for (Map.Entry<Long, Integer> entry
                : quantitiesByBookId.entrySet()) {

            Book book = books.findById(entry.getKey())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Ein Buch im Warenkorb wurde nicht gefunden."));

            CartItem item = new CartItem(
                    customer,
                    book,
                    entry.getValue());

            cartItems.save(item);
        }

        // 5. Änderungen innerhalb der Transaktion ausführen.
        cartItems.flush();

        return getCart(customer);
    }
}