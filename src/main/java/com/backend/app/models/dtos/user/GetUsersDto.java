package com.backend.app.models.dtos.user;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.common.PaginationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUsersDto {

    private Long idRole;

    public static DtoException<GetUsersDto> create(Long idRole) {
        return new DtoException<>(null, new GetUsersDto (idRole));
    }
}
