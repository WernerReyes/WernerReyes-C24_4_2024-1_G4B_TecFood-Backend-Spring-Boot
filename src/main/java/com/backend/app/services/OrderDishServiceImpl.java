package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IOrderDishService;
import com.backend.app.models.dtos.requests.orderDish.FindOrderDishesByUserRequest;
import com.backend.app.models.dtos.requests.orderDish.UpdateOrderDishStatusRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.models.dtos.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.persistence.entities.*;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.enums.EStatus;
import com.backend.app.persistence.repositories.CartDishRepository;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import com.backend.app.persistence.repositories.OrderDishRepository;
import com.backend.app.persistence.specifications.OrderDishSpecification;
import com.backend.app.utilities.UsageStatusUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderDishServiceImpl implements IOrderDishService {

    private final OrderDishRepository orderDishRepository;
    private final OrderDishItemRepository orderItemRepository;
    private final CartDishRepository cartDishRepository;
    private final DishRepository dishRepository;

    private final UserAuthenticationService userAuthenticationService;

    private final UsageStatusUtility usageStatusUtility;

    @Override
    @Transactional
    public ApiResponse<OrderDishEntity> createOrderDish() {
        UserEntity user = userAuthenticationService.find();
        List<CartDishEntity> cart = cartDishRepository.findByUser(user);
        if (cart.isEmpty()) throw CustomException.badRequest("Cart is empty");

        Double total = cart.stream().mapToDouble(cartItem -> cartItem.getDish().getPrice() * cartItem.getQuantity()).sum();

        //* Validate if dish stock is enough and dish is available
        for (CartDishEntity cartDish : cart) {
            DishEntity dish = dishRepository.findById(cartDish.getDish().getId()).orElse(null);
            if (dish == null) throw CustomException.badRequest("Dish not found");
            if (dish.getStock() < cartDish.getQuantity()) throw CustomException.badRequest("Dish: " + dish.getName() + " is out of stock");
            if (dish.getStatus() == EStatus.PRIVATE) throw CustomException.badRequest("Dish: " + dish.getName() + " is not available");
        }

        OrderDishEntity order = OrderDishEntity.builder()
                .user(user)
                .total(total)
                .orderDate(LocalDateTime.now())
                .status(EOrderDishStatus.PENDING)
                .build();
        orderDishRepository.save(order);

        for (CartDishEntity cartDish : cart) {
            DishEntity dish = dishRepository.findById(cartDish.getDish().getId()).orElse(null);
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

            //* Update dish usage status
            usageStatusUtility.updateDishUsageStatus(dish);
        }


        cartDishRepository.deleteAll(cart);

        return  new ApiResponse<>(EResponseStatus.SUCCESS, "Order created", order);
    }


    @Override
    public ApiResponse<EOrderDishStatus> updateOrderDishStatus(UpdateOrderDishStatusRequest updateOrderDishStatusRequest) {
        OrderDishEntity order = orderDishRepository.findById(updateOrderDishStatusRequest.getOrderDishId()).orElse(null);
        if (order == null) throw CustomException.badRequest("Order not found");

        if (updateOrderDishStatusRequest.getStatus().equals(EOrderDishStatus.CANCELLED)) {
            cancelOrder(order);
        } else {
            order.setStatus(updateOrderDishStatusRequest.getStatus());
            orderDishRepository.save(order);
        }

        return new ApiResponse<>(EResponseStatus.SUCCESS, "Order status updated", order.getStatus());
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
    public ApiResponse<OrderDishEntity> findById(Long id) {
        OrderDishEntity order = orderDishRepository.findById(id).orElse(null);
        if (order == null) throw CustomException.badRequest("Order not found");
        return new ApiResponse<>(EResponseStatus.SUCCESS, "Order found", order);
    }
    @Override
    public ApiResponse<PagedResponse<FindOrderDishesByUserResponse>> findOrderDishesByUser(FindOrderDishesByUserRequest findOrderDishesByUserRequest) {
        Pageable pageable = PageRequest.of(findOrderDishesByUserRequest.getPage() - 1, findOrderDishesByUserRequest.getLimit());
        Page<OrderDishEntity> orders = applySpecsAndPagination(pageable, findOrderDishesByUserRequest);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS, "Orders found",
                new PagedResponse<>(
                        new FindOrderDishesByUserResponse(orders.getContent(), findOrderDishesByUserRequest.getStatus()),
                        findOrderDishesByUserRequest.getPage(),
                        orders.getTotalPages(),
                        findOrderDishesByUserRequest.getLimit(),
                        (int) orders.getTotalElements(),
                        getNextUrl(findOrderDishesByUserRequest),
                        getPreviousUrl(findOrderDishesByUserRequest)
                    )
        );
    }

    private String getPreviousUrl(FindOrderDishesByUserRequest findOrderDishesByUserRequest) {
        return "/api/order-dish/user?page=" + (findOrderDishesByUserRequest.getPage() - 1) + "&limit=" + findOrderDishesByUserRequest.getLimit() +
                "&status=" + findOrderDishesByUserRequest.getStatus();
    }

    private String getNextUrl(FindOrderDishesByUserRequest findOrderDishesByUserRequest) {
        return "/api/order-dish/user?page=" + (findOrderDishesByUserRequest.getPage() + 1) + "&limit=" + findOrderDishesByUserRequest.getLimit() +
                "&status=" + findOrderDishesByUserRequest.getStatus();
    }

    private Page<OrderDishEntity> applySpecsAndPagination(Pageable pageable, FindOrderDishesByUserRequest findOrderDishesByUserRequest) {
        Specification<OrderDishEntity> spec = Specification.where(
                OrderDishSpecification.userEquals(userAuthenticationService.find())
        ).and(
                OrderDishSpecification.statusIn(findOrderDishesByUserRequest.getStatus())
        );
        return orderDishRepository.findAll(spec, pageable);
    }

}
