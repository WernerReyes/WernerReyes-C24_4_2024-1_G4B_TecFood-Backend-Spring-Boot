package com.backend.app.models.dtos.payment;


import com.backend.app.models.dtos.common.PaginationDto;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindPaymentByUserDto extends PaginationDto {
    @NotNull(message = "Status is required")
    private List<EPaymentStatus> status;

    public FindPaymentByUserDto(List<EPaymentStatus> status, Integer page, Integer limit) {
        super(page, limit);
        this.status = status;
    }
}
