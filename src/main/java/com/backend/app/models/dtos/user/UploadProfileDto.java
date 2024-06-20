package com.backend.app.models.dtos.user;

import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.common.UploadFileDto;
import com.backend.app.utilities.ValidationsUtility;

public class UploadProfileDto extends UploadFileDto {

    public static DtoException<UploadProfileDto> create(
            UploadProfileDto body
    ) throws IllegalAccessException {
        System.out.println("Body: " + body);
        if (ValidationsUtility.hasNullField(body)) return new DtoException<>("One or more fields are empty", null);
        DtoException<UploadFileDto> file =  UploadProfileDto.create(body.getFile(), body.getTypeFile(), body.getTypeFolder());
        if (file.getError() != null) return new DtoException<>(file.getError(), null);
        return new DtoException<>(null, body);
    }
}
