package br.com.eremate.ecommerce.repository;

import br.com.eremate.ecommerce.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
