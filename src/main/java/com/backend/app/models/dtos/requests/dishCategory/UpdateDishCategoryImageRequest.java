package com.backend.app.models.dtos.requests.dishCategory;

import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishCategoryImageRequest extends UploadImagesRequest {

    @NotNull(message = "Id is required")
    @Min(value = 1, message = "Id must be a positive integer")
    private Long dishCategoryId;

    public void validated() {
        RequestValidatorUtility.validate(this);
    }
}
