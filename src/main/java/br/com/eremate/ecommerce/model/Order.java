package br.com.eremate.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCriacao;

    private String status; // PENDENTE, PAGO, ENVIADO, ENTREGUE, etc.

    private Double total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}