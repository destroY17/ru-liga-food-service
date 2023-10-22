package ru.liga.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "order_items")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "order_items_seq_gen", sequenceName = "order_items_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_seq_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "restaurant_menu_item")
    private RestaurantMenuItem restaurantMenuItem;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

}
