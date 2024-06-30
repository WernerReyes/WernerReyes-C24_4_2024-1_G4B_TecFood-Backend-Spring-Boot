package com.backend.app.models.dtos.common;


import com.backend.app.persistence.enums.upload.ETypeFile;
import com.backend.app.persistence.enums.upload.ETypeFolder;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDto {

    @NotNull(message = "File is required")
    protected MultipartFile file;

    @NotNull(message = "Type file is required")
    protected ETypeFile typeFile;

    @NotNull(message = "Type folder is required")
    protected ETypeFolder typeFolder;
}
