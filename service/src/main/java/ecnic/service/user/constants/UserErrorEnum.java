package ecnic.service.user.constants;

import static ecnic.service.common.constants.ErrorCodeConstant.PREFIX_ERROR_CODE_USER;

import ecnic.service.common.exceptions.ErrorCode;
import java.util.Locale;
import lombok.Getter;

public enum UserErrorEnum implements ErrorCode {
    
    USER_NOT_FOUND(PREFIX_ERROR_CODE_USER + "0001", "User [%s] not found"),
    USERNAME_AND_PASSWORD_NOT_MATCH(PREFIX_ERROR_CODE_USER + "0002",
            "Login attempt failed due to an invalid password. Please check your password and try again.a"),
    YOUR_DEVICE_NOT_PERMITTED_TO_LOGIN(PREFIX_ERROR_CODE_USER + "0003",
            "Your device is not permitted to log in. Please verify your device settings or contact support for assistance."),
    ;
    private static final String MODULE = "USER-BIZ-TOOLS";
    
    @Getter
    private final String description;
    private final String code;
    
    UserErrorEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    @Override
    public String getLocalCode() {
        return code;
    }
    
    @Override
    public String getDescription(Locale locale) {
        return description;
    }
    
    @Override
    public String getModuleCode() {
        return MODULE;
    }
}
