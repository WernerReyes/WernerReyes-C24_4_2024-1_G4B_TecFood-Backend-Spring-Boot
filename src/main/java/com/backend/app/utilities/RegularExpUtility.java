package com.backend.app.utilities;

public class RegularExpUtility {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@tecsup\\.edu\\.pe$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    public static final String PHONE_NUMBER_REGEX = "^\\d{9}$";
    public static final String DNI_REGEX = "^\\d{8}$";
    public static final String URL_REGEX = "^(http|https):\\/\\/(localhost|[a-z0-9-]+(\\.[a-z0-9-]+)+)(:\\d{1,5})?(\\/.*)?$";
}
