package com.backend.app.services;

import com.backend.app.exception.CustomException;
import com.backend.app.models.ICartService;
import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cart.AddToCartResponse;
import com.backend.app.models.responses.cart.DeleteToCardResponse;
import com.backend.app.models.responses.cart.GetDishesCartResponse;
import com.backend.app.persistence.entities.CartEntity;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.CartRepository;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddToCartResponse addOneDishToCart(Long dishId) throws Exception {
        UserEntity user = userAuthentication();
        GetDishesCartResponse dishesByUser = getDishesCartByUser();
        if (dishesByUser.totalQuantity() + 1 > 5) throw CustomException.badRequest("You can't add more than 5 dishes");

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartEntity cart = cartRepository.findByUserAndDish(user, dish);
        if (cart != null) {
            System.out.println(cart.getQuantity());
            if(cart.getQuantity() + 1 > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
            return new AddToCartResponse(
                    cart.getDish().getName() + " added to cart",
                    cart
            );
        }

        cart = CartEntity.builder()
                .quantity(1)
                .dish(dish)
                .user(user)
                .build();
        cartRepository.save(cart);

        return new AddToCartResponse(
                cart.getDish().getName() + " added to cart",
                cart
        );
    }

    @Override
    public AddToCartResponse addManyDishesToCart(AddToCartDto addToCartDto) throws Exception {
        UserEntity user = userAuthentication();

        DishEntity dish = dishRepository.findById(addToCartDto.getDishId()).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartEntity cart = cartRepository.findByUserAndDish(user, dish);
        if (cart != null) {
            if(cart.getQuantity() + addToCartDto.getQuantity() > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
            cart.setQuantity(cart.getQuantity() + addToCartDto.getQuantity());
            cartRepository.save(cart);
            return new AddToCartResponse(
                    cart.getDish().getName() + " added to cart",
                    cart
            );
        }

        cart = CartEntity.builder()
                .quantity(addToCartDto.getQuantity())
                .dish(dish)
                .user(user)
                .build();
        cartRepository.save(cart);

        return new AddToCartResponse(
                cart.getDish().getName() + " added to cart",
                cart
        );

    }

    @Override
    public DeleteToCardResponse deleteOneDishFromCart(Long dishId) throws Exception {
        UserEntity user = userAuthentication();

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartEntity cart = cartRepository.findByUserAndDish(user, dish);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");

        if (cart.getQuantity() == 1) {
            cartRepository.delete(cart);
            return new DeleteToCardResponse(
                    "Dish deleted from cart",
                    cart.getQuantity()
            );
        }

        cart.setQuantity(cart.getQuantity() - 1);
        cartRepository.save(cart);

        return new DeleteToCardResponse(
                "Dish deleted from cart",
                cart.getQuantity()
        );
    }

    @Override
    public DeleteToCardResponse deleteAllDishesFromCart(Long dishId) throws Exception {
        UserEntity user = userAuthentication();

        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        CartEntity cart = cartRepository.findByUserAndDish(user, dish);
        if (cart == null) throw CustomException.badRequest("Dish not found in cart");
        cartRepository.delete(cart);

        return new DeleteToCardResponse(
                "Dish deleted from cart",
                cart.getQuantity()
        );
    }

    @Override
    public GetDishesCartResponse getDishesCartByUser() throws Exception {
        UserEntity user = userAuthentication();
        List<CartEntity> cart = cartRepository.findByUser(user);
        int totalQuantity = cart.stream().mapToInt(CartEntity::getQuantity).sum();
        if (totalQuantity > 5) throw CustomException.badRequest("You can't add more than 5 dishes");
        double totalPayment = cart.stream().mapToDouble(value -> value.getDish().getPrice() * value.getQuantity()).sum();
        return new GetDishesCartResponse(
                "Dishes in cart",
                cart,
                totalQuantity,
                totalPayment

        );
    }

    private UserEntity userAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        UserEntity user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        if (user == null) throw CustomException.badRequest("User not found");
        return user;
    }
}
