package br.com.eremate.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Double preco;

    private Integer estoque;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category categoria;
}
