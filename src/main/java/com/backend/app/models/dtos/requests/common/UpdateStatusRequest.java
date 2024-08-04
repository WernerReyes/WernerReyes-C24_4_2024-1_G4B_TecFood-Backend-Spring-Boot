package com.backend.app.models.dtos.requests.common;

import com.backend.app.persistence.enums.EStatus;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateStatusRequest {

    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Status is required")
    private EStatus status;

    public UpdateStatusRequest(Long id, EStatus status) {
        this.id = id;
        this.status = status;

        RequestValidatorUtility.validate(this);
    }

}
