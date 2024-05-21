package com.backend.app.models.dtos.card;

import com.backend.app.exception.DtoException;
import com.backend.app.utilities.ValidationsUtility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddToCartDto {
    private Long dishId;
    private int quantity;

    public static DtoException<AddToCartDto> create(AddToCartDto body) throws IllegalAccessException {
        if(ValidationsUtility.hasNullField(body)) return new DtoException<>("One or more fields are empty", null);
        if(body.getQuantity() > 5) return new DtoException<>("You can't add more than 5 dish", null);
        return new DtoException<>(null, body);
    }
}
