package com.backend.app.models.dtos.requests.user;
import com.backend.app.exceptions.DtoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUsersRequest {

    private Long idRole;

    public static DtoException<GetUsersRequest> create(Long idRole) {
        return new DtoException<>(null, new GetUsersRequest(idRole));
    }
}
