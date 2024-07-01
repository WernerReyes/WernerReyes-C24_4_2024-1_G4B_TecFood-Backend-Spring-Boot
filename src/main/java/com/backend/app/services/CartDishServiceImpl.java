package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.ICartDishService;
import com.backend.app.models.dtos.requests.cartDish.AddToCartRequest;
import com.backend.app.models.dtos.responses.cartDish.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.CartDishEntity;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.repositories.CartDishRepository;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.utilities.UsageStatusUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartDishServiceImpl implements ICartDishService {

    private final CartDishRepository cartDishRepository;
    private final DishRepository dishRepository;
    private final UserAuthenticationService userAuthenticationService;

    private final UsageStatusUtility usageStatusUtility;

    @Override
    public ApiResponse<CartDishEntity> addOneDishToCart(Long dishId) {
        UserEntity user = userAuthenticationService.find();
        ApiResponse<FindDishesToCartByUserResponse> dishesByUser = findDishesCartByUser();
        if (dishesByUser.data().totalQuantity() + 1 > 5) throw CustomException.badRequest("You can't add more than 5 dishes");

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart != null) {
            if(cart.getQuantity() + 1 > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
            cart.setQuantity(cart.getQuantity() + 1);
            cartDishRepository.save(cart);
            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    "Dish added to cart",
                    cart
            );
        }

        cart = CartDishEntity.builder()
                .quantity(1)
                .dish(dish)
                .user(user)
                .build();
        cartDishRepository.save(cart);

        //* Update dish usage status
        usageStatusUtility.updateDishUsageStatus(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish added to cart",
                cart
        );
    }

    @Override
    public ApiResponse<CartDishEntity> addManyDishesToCart(AddToCartRequest addToCartRequest)  {
        UserEntity user = userAuthenticationService.find();

        DishEntity dish = dishRepository.findById(addToCartRequest.getDishId()).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart != null) {
            if(cart.getQuantity() + addToCartRequest.getQuantity() > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
            cart.setQuantity(cart.getQuantity() + addToCartRequest.getQuantity());
            cartDishRepository.save(cart);
            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    "Dish added to cart",
                    cart
            );
        }

        cart = CartDishEntity.builder()
                .quantity(addToCartRequest.getQuantity())
                .dish(dish)
                .user(user)
                .build();
        cartDishRepository.save(cart);

        //* Update dish usage status
        usageStatusUtility.updateDishUsageStatus(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish added to cart",
                cart
        );

    }

    @Override
    public ApiResponse<Integer> deleteOneDishFromCart(Long dishId) {
        UserEntity user = userAuthenticationService.find();

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");

        if (cart.getQuantity() == 1) {
            cartDishRepository.delete(cart);

            //* Update dish usage status
            usageStatusUtility.updateDishUsageStatus(dish);
            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    "Dish deleted from cart",
                    cart.getQuantity()
            );
        }

        cart.setQuantity(cart.getQuantity() - 1);
        cartDishRepository.save(cart);

        //* Update dish usage status
        usageStatusUtility.updateDishUsageStatus(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish deleted from cart",
                cart.getQuantity()
        );
    }

    @Override
    public ApiResponse<Integer> deleteAllDishesFromCart(Long dishId) {
        UserEntity user = userAuthenticationService.find();

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");

        cartDishRepository.delete(cart);

        //* Update dish usage status
        usageStatusUtility.updateDishUsageStatus(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish deleted from cart",
                cart.getQuantity()
        );
    }

    @Override
    public ApiResponse<FindDishesToCartByUserResponse> findDishesCartByUser() {
        UserEntity user = userAuthenticationService.find();

        List<CartDishEntity> cart = cartDishRepository.findByUser(user);
        int totalQuantity = cart.stream().mapToInt(CartDishEntity::getQuantity).sum();
        if (totalQuantity > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
        double totalPayment = cart.stream().mapToDouble(value -> value.getDish().getPrice() * value.getQuantity()).sum();
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dishes found in cart",
                new FindDishesToCartByUserResponse(
                        cart,
                        totalQuantity,
                        totalPayment
                )
        );
    }

    @Override
    public ApiResponse<CartDishEntity> findDishToCartByDishId(Long dishId) {
        CartDishEntity cart = cartDishRepository.findByDish_Id(dishId);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish found in cart",
                cart
        );
    }

    @Override
    public ApiResponse<Integer> findTotalDishesCartByUser() {
        UserEntity user = userAuthenticationService.find();
        List<CartDishEntity> cart = cartDishRepository.findByUser(user);
        int totalQuantity = cart.stream().mapToInt(CartDishEntity::getQuantity).sum();
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Total dishes found in cart",
                totalQuantity
        );
    }

}
