package com.backend.app.models.dtos.user;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.common.PaginationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUsersDto extends PaginationDto {
    private Long idRole;

    private GetUsersDto(int page, int limit, Long idRole) {
        super(page, limit);
        this.idRole = idRole;
    }

    public static DtoException<GetUsersDto> create(int page, int limit, Long idRole) {
        DtoException<PaginationDto> paginationDto = PaginationDto.create(page, limit);
        if (paginationDto.getError() != null) return new DtoException<>(paginationDto.getError(), null);
        if(idRole != null) {
            if (idRole < 1) return new DtoException<>("Invalid category id", null);
        }
        return new DtoException<>(null, new GetUsersDto (page, limit, idRole));
    }
}
