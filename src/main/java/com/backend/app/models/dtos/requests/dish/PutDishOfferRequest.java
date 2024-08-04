package com.backend.app.models.dtos.requests.dish;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.dtos.annotations.FutureOrPresent;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PutDishOfferRequest {

    @NotNull(message = "Dish id is required")
    private Long dishId;

    @NotNull(message = "Discount percentage is required")
    @Min(value = 1, message = "Discount percentage must be greater than 0")
    @Max(value = 100, message = "Discount percentage must be less than 100")
    private Double discountPercentage;

    @FutureOrPresent(message = "Sale start date must be in the future or present")
    @NotNull(message = "Sale start date is required")
    private LocalDateTime saleStartDate;

    @FutureOrPresent(message = "Sale end date must be in the future or present")
    @NotNull(message = "Sale end date is required")
    private LocalDateTime saleEndDate;

    public PutDishOfferRequest(Long dishId, Double discountPercentage, LocalDateTime saleStartDate, LocalDateTime saleEndDate) {
        this.dishId = dishId;
        this.discountPercentage = discountPercentage;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;

        validate();
    }

    private void validate() {
        RequestValidatorUtility.validate(this);

        if (this.saleStartDate.isAfter(this.saleEndDate))
            throw CustomException.badRequest("Sale start date must be before sale end date");
        if (this.saleStartDate.equals(this.saleEndDate))
            throw CustomException.badRequest("Sale start date must be different from sale end date");
    }



}
