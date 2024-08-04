package com.backend.app.models.dtos.requests.common;

import com.backend.app.models.dtos.annotations.ValidFileType;
import com.backend.app.persistence.enums.EFileType;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadImagesRequest {

    @NotNull(message = "File is required")
    @Size(min = 1, max = 5, message = "At least one file is required and at most 5")
    @ValidFileType(value = {EFileType.IMAGE},
            message = "Files must be a valid document or image"

    )
    protected List<MultipartFile> files;

    @NotNull(message = "Type file is required")
    protected EFileType typeFile = EFileType.IMAGE;

    public UploadImagesRequest(MultipartFile file) {
        this.files = List.of(file);
        RequestValidatorUtility.validate(this);
    }

    public UploadImagesRequest(List<MultipartFile> files) {
        this.files = files;

        RequestValidatorUtility.validate(this);
    }
}