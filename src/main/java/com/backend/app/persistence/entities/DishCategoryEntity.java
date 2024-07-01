package com.backend.app.persistence.entities;

import com.backend.app.persistence.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(exclude = "dishes")
@Table(name="dish_category")
public class DishCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_category_id", nullable = false)
    private Long id;

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private EStatus status;

    @Column(name="is_used", nullable = false)
    private Boolean isUsed;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private List<DishEntity> dishes;

}
