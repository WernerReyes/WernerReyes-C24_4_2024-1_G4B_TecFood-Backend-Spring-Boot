package com.backend.app.models.dtos.common;

import com.backend.app.exceptions.DtoException;
import com.backend.app.persistence.enums.upload.ETypeFile;
import com.backend.app.persistence.enums.upload.ETypeFolder;
import com.backend.app.utilities.ValidationsUtility;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFileDto {
    private MultipartFile file;
    private ETypeFile typeFile;
    private ETypeFolder typeFolder;

    public static DtoException<UploadFileDto> create(
            MultipartFile file,
            ETypeFile typeFile,
            ETypeFolder typeFolder

    ) {
        if (file == null) return new DtoException<>("File is required", null);
        if (typeFile == null) return new DtoException<>("Type file is required", null);
        if (typeFolder == null) return new DtoException<>("Type folder is required", null);
        if (!ValidationsUtility.isValidImageFile(file)) return new DtoException<>("Invalid file", null);
        if (!ValidationsUtility.isValidFolder(typeFolder)) return new DtoException<>("Invalid folder", null);
        return new DtoException<>(null, new UploadFileDto(file, typeFile, typeFolder));
    }
}
