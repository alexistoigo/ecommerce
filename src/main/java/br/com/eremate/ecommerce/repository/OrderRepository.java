package br.com.eremate.ecommerce.repository;

import br.com.eremate.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
