package com.backend.app.services;

import com.backend.app.models.IUploadFileService;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EFileType;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final UserAuthenticationService userAuthenticationService;
    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, EFileType typeFile) {
        try {
            UserEntity user = userAuthenticationService.find();
            String url = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", user.getRole().getName() + "/" + user.getId() + "/" + typeFile.name(),
                            "public_id", user.getId() + "_" + System.currentTimeMillis()
                    )
            ).get("url").toString();

            //* From HTTP to HTTPS
            return url.replace("http", "https");
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file");
        }
    }

    public List<String> uploadFiles(List<MultipartFile> files, EFileType typeFile) {
        try {
            return files.stream().map(file -> uploadFile(file, typeFile)).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file");
        }
    }

    public String uploadInvoice(JasperPrint jasperPrint) {
        try {
            UserEntity user = userAuthenticationService.find();
            File pdf = File.createTempFile("invoice", ".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdf.getAbsolutePath());

            String url =  cloudinary.uploader().upload(
                    pdf,
                    Map.of(
                            "folder", user.getRole().getName() + "/" + user.getId() + "/" + EFileType.DOCUMENT.name(),
                            "public_id", user.getId() + "_invoice" + System.currentTimeMillis()
                    )
            ).get("url").toString();

            //* From HTTP to HTTPS
            return url.replace("http", "https");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error uploading file");
        }
    }

    public void deleteFile(String url, EFileType typeFile) {
        UserEntity user = userAuthenticationService.find();
        String imageName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        String publicId = user.getRole().getName() + "/" + user.getId() + "/" + typeFile.name() + "/" + imageName;
        try {
            cloudinary.uploader().destroy(
                    publicId,
                    Map.of("invalidate", true)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file");
        }
    }
}
