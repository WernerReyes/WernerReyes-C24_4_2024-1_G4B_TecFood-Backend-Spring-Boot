package com.backend.app.models.dtos.requests.auth;
import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.utilities.RequestValidatorUtility;
import com.backend.app.utilities.RegularExpUtility;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
public class LoginGoogleRequest extends AuthRequest {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @NotNullAndNotEmpty(message = "Image URL is required")
    @Pattern(message = "Image URL is not valid" ,regexp = RegularExpUtility.URL_REGEX)
    private String imgUrl;

    @NotNull(message = "Email must be verified")
    @AssertTrue(message = "Email must be verified")
    private Boolean isEmailVerified;

    @NotNull(message = "Google account is required")
    @AssertTrue(message = "Google account is required")
    private Boolean isGoogleAccount;

    @NotNullAndNotEmpty(message = "First name is required")
    @Size(min = 3, message = "First name must have at least 3 characters")
    private String firstName;

    @NotNullAndNotEmpty(message = "Last name is required")
    @Size(min = 3, message = "Last name must have at least 3 characters")
    private String lastName;

    public LoginGoogleRequest(String email, String imgUrl, Boolean isEmailVerified, Boolean isGoogleAccount, String firstName, String lastName) {
        super(email, RandomStringUtils.random(10, LETTERS)+System.currentTimeMillis());
        this.imgUrl = imgUrl;
        this.isEmailVerified = isEmailVerified;
        this.isGoogleAccount = isGoogleAccount;
        this.firstName = firstName;
        this.lastName = lastName;

        RequestValidatorUtility.validate(this);
    }





}


