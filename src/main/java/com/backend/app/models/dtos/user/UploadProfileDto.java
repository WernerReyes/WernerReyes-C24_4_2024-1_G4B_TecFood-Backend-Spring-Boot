package com.backend.app.models.dtos.user;


import com.backend.app.models.dtos.common.UploadFileDto;
import com.backend.app.persistence.enums.upload.ETypeFile;
import com.backend.app.persistence.enums.upload.ETypeFolder;
import org.springframework.web.multipart.MultipartFile;

public class UploadProfileDto extends UploadFileDto {
    public UploadProfileDto(MultipartFile file) {
        super(file, ETypeFile.IMAGE, ETypeFolder.USER);
    }
}
