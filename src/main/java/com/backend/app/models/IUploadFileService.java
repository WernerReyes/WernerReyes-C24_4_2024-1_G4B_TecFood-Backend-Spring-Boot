package com.backend.app.models;

import com.backend.app.persistence.enums.EFileType;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUploadFileService {
    String uploadFile(MultipartFile file, EFileType typeFile);
    List<String> uploadFiles(java.util.List<MultipartFile> files, EFileType typeFile);
    String uploadInvoice(JasperPrint jasperPrint);
    void deleteFile(String url, EFileType typeFile);
}
