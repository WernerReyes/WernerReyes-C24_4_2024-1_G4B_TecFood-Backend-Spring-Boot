package com.backend.app.models.dtos.paypal;

import com.backend.app.models.dtos.NotNullAndNotEmpty;
import com.backend.app.utilities.RegularExpUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentDto {

    @NotNull(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNullAndNotEmpty(message = "URL capture is required")
    @Pattern(regexp = RegularExpUtility.URL_REGEX, message = "URL capture must be a valid URL")
    private String urlCapture;

    @NotNullAndNotEmpty(message = "URL cancel is required")
    @Pattern(regexp = RegularExpUtility.URL_REGEX, message = "URL cancel must be a valid URL")
    private String urlCancel;
}
