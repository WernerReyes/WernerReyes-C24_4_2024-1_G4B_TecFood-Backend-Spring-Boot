package com.backend.app.persistence.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="dish")
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dish_id")
    private Long idDish;

    @Column(name="name")
    private String name;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name = "stock")
    private Integer stock;

    @Column(name="description")
    private String description;

    @Column(name = "price")
    private Double price;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_category_id", nullable = false)
    private DishCategoryEntity category;


}
