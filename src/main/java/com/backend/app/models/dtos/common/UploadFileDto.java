package com.backend.app.models.dtos.common;

import com.backend.app.models.dtos.NotNullAndNotEmpty;
import com.backend.app.persistence.enums.upload.ETypeFile;
import com.backend.app.persistence.enums.upload.ETypeFolder;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDto {

    @NotNullAndNotEmpty(message = "File is required")
    protected MultipartFile file;

    @NotNullAndNotEmpty(message = "Type file is required")
    protected ETypeFile typeFile;

    @NotNullAndNotEmpty(message = "Type folder is required")
    protected ETypeFolder typeFolder;
}
