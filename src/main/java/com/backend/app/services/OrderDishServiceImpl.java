package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IOrderDishService;
import com.backend.app.models.dtos.dish.GetDishesDto;
import com.backend.app.models.dtos.orderDish.FindOrderDishesByUserDto;
import com.backend.app.models.dtos.orderDish.UpdateOrderDishStatusDto;
import com.backend.app.models.responses.orderDish.CreateOrderDishResponse;
import com.backend.app.models.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.models.responses.orderDish.UpdateOrderDishStatusResponse;
import com.backend.app.persistence.entities.*;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.persistence.repositories.CartDishRepository;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import com.backend.app.persistence.repositories.OrderDishRepository;
import com.backend.app.persistence.specifications.DishSpecification;
import com.backend.app.persistence.specifications.OrderDishSpecification;
import com.backend.app.utilities.UserAuthenticationUtility;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderDishServiceImpl implements IOrderDishService {
    @Autowired
    private OrderDishRepository orderDishRepository;

    @Autowired
    private OrderDishItemRepository orderItemRepository;

    @Autowired
    private CartDishRepository cartDishRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private UserAuthenticationUtility userAuthenticationUtility;

    @Override
    @Transactional
    public CreateOrderDishResponse createOrderDish() {
        UserEntity user = userAuthenticationUtility.find();
        List<CartDishEntity> cart = cartDishRepository.findByUser(user);
        if (cart.isEmpty()) throw CustomException.badRequest("Cart is empty");

        Double total = cart.stream().mapToDouble(cartItem -> cartItem.getDish().getPrice() * cartItem.getQuantity()).sum() ;

        OrderDishEntity order = OrderDishEntity.builder()
                .user(user)
                .total(total)
                .orderDate(LocalDateTime.now())
                .status(EOrderDishStatus.PENDING)
                .build();
        orderDishRepository.save(order);

        for (CartDishEntity cartDish : cart) {
            DishEntity dish = dishRepository.findById(cartDish.getDish().getIdDish()).orElse(null);
            if (dish == null) throw CustomException.badRequest("Dish not found");
            OrderDishItemEntity orderItem = OrderDishItemEntity.builder()
                    .orderDish(order)
                    .price(cartDish.getDish().getPrice() * cartDish.getQuantity())
                    .dish(cartDish.getDish())
                    .quantity(cartDish.getQuantity())
                    .build();

            dish.setStock(dish.getStock() - cartDish.getQuantity());
            dishRepository.save(dish);

            orderItemRepository.save(orderItem);
        }


        cartDishRepository.deleteAll(cart);

        return new CreateOrderDishResponse("Order created", order);
    }


    @Override
    public UpdateOrderDishStatusResponse updateOrderDishStatus(UpdateOrderDishStatusDto updateOrderDishStatusDto) {
        OrderDishEntity order = orderDishRepository.findById(updateOrderDishStatusDto.getOrderDishId()).orElse(null);
        if (order == null) throw CustomException.badRequest("Order not found");

        if (updateOrderDishStatusDto.getStatus().equals(EOrderDishStatus.CANCELLED)) {
            cancelOrder(order);
        } else {
            order.setStatus(updateOrderDishStatusDto.getStatus());
            orderDishRepository.save(order);
        }

        return new UpdateOrderDishStatusResponse("Order status updated", order.getStatus());
    }

    private void cancelOrder(OrderDishEntity order) {
        List<OrderDishItemEntity> orderItems = orderItemRepository.findByOrderDish(order);
        for (OrderDishItemEntity orderItem : orderItems) {
            DishEntity dish = orderItem.getDish();
            dish.setStock(dish.getStock() + orderItem.getQuantity());
            dishRepository.save(dish);
        }
        order.setStatus(EOrderDishStatus.CANCELLED);
        orderDishRepository.save(order);
    }

    @Override
    public FindOrderDishesByUserResponse findOrderDishesByUser(FindOrderDishesByUserDto findOrderDishesByUserDto) {
        Pageable pageable = PageRequest.of(findOrderDishesByUserDto.getPage() - 1, findOrderDishesByUserDto.getLimit());
        Page<OrderDishEntity> orders = filters(pageable, findOrderDishesByUserDto);

        return new FindOrderDishesByUserResponse(
                "User orders",
                orders.getContent(),
                findOrderDishesByUserDto.getStatus(),
                findOrderDishesByUserDto.getPage(),
                (int) orders.getTotalElements(),
                findOrderDishesByUserDto.getLimit(),
                orders.hasNext() ? getNextUrl(findOrderDishesByUserDto) : null,
                orders.hasPrevious() ? getPreviousUrl(findOrderDishesByUserDto) : null
                );
    }

    private String getPreviousUrl(FindOrderDishesByUserDto findOrderDishesByUserDto) {
        return "/api/order-dish/user?page=" + (findOrderDishesByUserDto.getPage() - 1) + "&limit=" + findOrderDishesByUserDto.getLimit() +
                "&status=" + findOrderDishesByUserDto.getStatus();
    }

    private String getNextUrl(FindOrderDishesByUserDto findOrderDishesByUserDto) {
        return "/api/order-dish/user?page=" + (findOrderDishesByUserDto.getPage() + 1) + "&limit=" + findOrderDishesByUserDto.getLimit() +
                "&status=" + findOrderDishesByUserDto.getStatus();
    }

    private Page<OrderDishEntity> filters(Pageable pageable, FindOrderDishesByUserDto findOrderDishesByUserDto) {
        Specification<OrderDishEntity> spec = Specification.where(
                OrderDishSpecification.userEquals(userAuthenticationUtility.find())
        ).and(
                OrderDishSpecification.statusIn(findOrderDishesByUserDto.getStatus())
        );
        return orderDishRepository.findAll(spec, pageable);
    }

}
