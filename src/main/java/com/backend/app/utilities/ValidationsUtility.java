package com.backend.app.utilities;

import java.lang.reflect.Field;

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
        public static boolean isFieldEmpty(String field) { return field.isBlank(); }

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

        public static boolean isUsernameValid(String username) {
            return username.matches("^[a-zA-Z0-9_]{5,}[a-zA-Z]+[0-9]*$");
        }

        public static boolean isPhoneNumberValid(String phoneNumber) {
            return phoneNumber.matches("^\\d{9}$");
        }

        public static boolean isDniValid(String dni) {
            return dni.matches("^\\d{8}$");
        }
}
