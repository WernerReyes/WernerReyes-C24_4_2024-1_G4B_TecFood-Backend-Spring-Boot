package com.backend.app.models.dtos.requests.dish;

import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishImageRequest extends UploadImagesRequest {
    @NotNull(message = "Dish ID is required")
    private Long dishId;

    @Min(value = 1, message = "Image ID must be greater than 0")
    private Long imageIdToUpdate;

    public void validate() {
        RequestValidatorUtility.validate(this);
    }

}
