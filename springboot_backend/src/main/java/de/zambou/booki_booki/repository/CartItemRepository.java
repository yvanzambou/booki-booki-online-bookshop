
package de.zambou.booki_booki.repository;

import de.zambou.booki_booki.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCustomerIdOrderByIdAsc(Long customerId);

    @Modifying
    @Query("DELETE FROM CartItem item WHERE item.customer.id = :customerId")
    int deleteByCustomerId(@Param("customerId") Long customerId);
}