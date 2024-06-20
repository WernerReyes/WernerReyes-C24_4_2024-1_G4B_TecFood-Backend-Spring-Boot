package com.backend.app.utilities;

import com.backend.app.persistence.enums.upload.ETypeFolder;
import com.backend.app.persistence.enums.upload.ETypeImage;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ValidationsUtility {

        public static boolean hasNullField(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true); // You might want to set modifier to public first.
            if (field.get(obj) == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAllNullFields(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true); // You might want to set modifier to public first.
            if (field.get(obj) != null) {
                return false;
            }
        }
        return true;
    }

        public static boolean isFieldEmpty(String field) { return field.isBlank() || field.isEmpty(); }

        public static boolean isNameValid(String name) {
            return name.matches("^.{3,}$");
        }

        public static boolean isUrlValid(String url) {
            return url.matches("^https?:\\/\\/[a-z0-9-]+(\\.[a-z0-9-]+)+(:\\d{1,5})?(\\/.*)?$");
        }

        public static boolean isEmailValid(String email) {
            return email.matches("^[A-Za-z0-9+_.-]+@tecsup\\.edu\\.pe$");
        }

        public static boolean isPasswordValid(String password) {
            return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
        }

        public static boolean isPhoneNumberValid(String phoneNumber) {
            return phoneNumber.matches("^\\d{9}$");
        }

        public static boolean isDniValid(String dni) {
            return dni.matches("^\\d{8}$");
        }

        public static boolean isValidImageFile(MultipartFile file) {
            ETypeImage typeImage = Arrays.stream(ETypeImage.values()).filter(
                    e -> e.name().equals(file.getContentType().split("/")[1].toUpperCase())
            ).findFirst().orElse(null);
            return typeImage != null;
        }

        public static boolean isValidFolder(ETypeFolder typeFolder) {
            return Arrays.stream(ETypeFolder.values()).filter(
                    e -> e.equals(typeFolder)
            ).findFirst().orElse(null) != null;
        }

}
