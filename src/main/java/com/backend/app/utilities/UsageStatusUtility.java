package com.backend.app.utilities;

import com.backend.app.persistence.entities.DishCategoryEntity;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.repositories.CartDishRepository;
import com.backend.app.persistence.repositories.DishCategoryRepository;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UsageStatusUtility {

    private final DishCategoryRepository dishCategoryRepository;
    private final DishRepository dishRepository;
    private final CartDishRepository cartDishRepository;
    private final OrderDishItemRepository orderDishItemRepository;


    public void updateDishCategoryUsageStatus(DishCategoryEntity dishCategory) {
        boolean isUsed = dishRepository.existsByCategoriesId(dishCategory.getId());
        if (dishCategory.getIsUsed().equals(isUsed)) return;
        dishCategory.setIsUsed(isUsed);
        dishCategoryRepository.save(dishCategory);
    }

    public void updateDishUsageStatus(DishEntity dish) {
        boolean isUsed = cartDishRepository.existsByDishId(dish.getId()) || orderDishItemRepository.existsByDishId(dish.getId());
        if (dish.getIsUsed().equals(isUsed)) return;
        dish.setIsUsed(isUsed);
        dishRepository.save(dish);
    }



}
