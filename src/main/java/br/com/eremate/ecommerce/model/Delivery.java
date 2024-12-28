package br.com.eremate.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusEntrega; // AGUARDANDO_ENVIO, EM_TRANSITO, ENTREGUE...

    private LocalDateTime dataUltimaAtualizacao;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}