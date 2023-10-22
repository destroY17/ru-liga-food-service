package ru.liga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "restaurant_menu_items")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuItem {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "restaurant_menu_items_seq_gen", sequenceName = "restaurant_menu_items_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_menu_items_seq_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;
}
