package com.backend.app.utilities;

import com.backend.app.persistence.enums.upload.ETypeFile;
import com.backend.app.persistence.enums.upload.ETypeFolder;
import com.cloudinary.Cloudinary;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Component
public class CloudinaryUtility {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, ETypeFile typeFile, ETypeFolder folder, Integer publicId) {
        try {
            return cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", folder.name() + "/" + publicId.toString() + "/" + typeFile.name(),
                            "public_id", publicId.toString()
                    )
            ).get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file");
        }
    }

    public String uploadInvoice(JasperPrint jasperPrint, Integer publicId) {
        try {
            File pdf = File.createTempFile("invoice", ".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdf.getAbsolutePath());

            return cloudinary.uploader().upload(
                    pdf,
                    Map.of(
                            "folder", ETypeFolder.USER.name() + "/" + publicId.toString() + "/" + ETypeFile.DOCUMENT.name(),
                            "public_id", publicId + "_invoice" + System.currentTimeMillis()
                    )
            ).get("url").toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error uploading file");
        }
    }

    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId,
                    Map.of("invalidate", true)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file");
        }
    }

}
