package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.ICartDishService;
import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cartDish.*;
import com.backend.app.persistence.entities.CartDishEntity;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.CartDishRepository;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.utilities.UserAuthenticationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDishServiceImpl implements ICartDishService {

    @Autowired
    private CartDishRepository cartDishRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private UserAuthenticationUtility userAuthenticationUtility;

    @Override
    public AddToCartResponse addOneDishToCart(Long dishId) throws Exception {
        UserEntity user = userAuthenticationUtility.find();
        FindDishesToCartResponse dishesByUser = findDishesCartByUser();
        if (dishesByUser.totalQuantity() + 1 > 5) throw CustomException.badRequest("You can't add more than 5 dishes");

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart != null) {
            System.out.println(cart.getQuantity());
            if(cart.getQuantity() + 1 > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
            cart.setQuantity(cart.getQuantity() + 1);
            cartDishRepository.save(cart);
            return new AddToCartResponse(
                    cart.getDish().getName() + " added to cart",
                    cart
            );
        }

        cart = CartDishEntity.builder()
                .quantity(1)
                .dish(dish)
                .user(user)
                .build();
        cartDishRepository.save(cart);

        return new AddToCartResponse(
                cart.getDish().getName() + " added to cart",
                cart
        );
    }

    @Override
    public AddToCartResponse addManyDishesToCart(AddToCartDto addToCartDto) throws Exception {
        UserEntity user = userAuthenticationUtility.find();

        DishEntity dish = dishRepository.findById(addToCartDto.getDishId()).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart != null) {
            if(cart.getQuantity() + addToCartDto.getQuantity() > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
            cart.setQuantity(cart.getQuantity() + addToCartDto.getQuantity());
            cartDishRepository.save(cart);
            return new AddToCartResponse(
                    cart.getDish().getName() + " added to cart",
                    cart
            );
        }

        cart = CartDishEntity.builder()
                .quantity(addToCartDto.getQuantity())
                .dish(dish)
                .user(user)
                .build();
        cartDishRepository.save(cart);

        return new AddToCartResponse(
                cart.getDish().getName() + " added to cart",
                cart
        );

    }

    @Override
    public DeleteToCardResponse deleteOneDishFromCart(Long dishId) throws Exception {
        UserEntity user = userAuthenticationUtility.find();

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");

        if (cart.getQuantity() == 1) {
            cartDishRepository.delete(cart);
            return new DeleteToCardResponse(
                    "Dish deleted from cart",
                    cart.getQuantity()
            );
        }

        cart.setQuantity(cart.getQuantity() - 1);
        cartDishRepository.save(cart);

        return new DeleteToCardResponse(
                "Dish deleted from cart",
                cart.getQuantity()
        );
    }

    @Override
    public DeleteToCardResponse deleteAllDishesFromCart(Long dishId) throws Exception {
        UserEntity user = userAuthenticationUtility.find();

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartDishEntity cart = cartDishRepository.findByUserAndDish(user, dish);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");
        cartDishRepository.delete(cart);

        return new DeleteToCardResponse(
                "Dish deleted from cart",
                cart.getQuantity()
        );
    }

    @Override
    public FindDishesToCartResponse findDishesCartByUser() throws Exception {
        UserEntity user = userAuthenticationUtility.find();
        List<CartDishEntity> cart = cartDishRepository.findByUser(user);
        int totalQuantity = cart.stream().mapToInt(CartDishEntity::getQuantity).sum();
        if (totalQuantity > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
        double totalPayment = cart.stream().mapToDouble(value -> value.getDish().getPrice() * value.getQuantity()).sum();
        return new FindDishesToCartResponse(
                "Dishes in cart",
                cart,
                totalQuantity,
                totalPayment

        );
    }

    @Override
    public FindDishToCartResponse findDishToCartByDishId(Long dishId) throws Exception {
        CartDishEntity cart = cartDishRepository.findByDish_IdDish(dishId);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");
        return new FindDishToCartResponse(
                "Dish found in cart",
                cart
        );
    }

    @Override
    public FindTotalDishesToCartResponse findTotalDishesCartByUser() throws Exception {
        UserEntity user = userAuthenticationUtility.find();
        List<CartDishEntity> cart = cartDishRepository.findByUser(user);
        int totalQuantity = cart.stream().mapToInt(CartDishEntity::getQuantity).sum();
        return new FindTotalDishesToCartResponse(
                "Total dishes in cart",
                totalQuantity
        );
    }

}
