package com.backend.app.models.dtos.requests.paypal;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.utilities.RequestValidatorUtility;
import com.backend.app.utilities.RegularExpUtility;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotNull(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNullAndNotEmpty(message = "URL capture is required")
    @Pattern(regexp = RegularExpUtility.URL_REGEX, message = "URL capture must be a valid URL")
    private String urlCapture;

    @NotNullAndNotEmpty(message = "URL cancel is required")
    @Pattern(regexp = RegularExpUtility.URL_REGEX, message = "URL cancel must be a valid URL")
    private String urlCancel;

    public CreatePaymentRequest(Long orderDishId, String urlCapture, String urlCancel) {
        this.orderDishId = orderDishId;
        this.urlCapture = urlCapture;
        this.urlCancel = urlCancel;

        RequestValidatorUtility.validate(this);
    }
}
