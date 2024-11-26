package ecnic.service.common.exceptions;

import static ecnic.service.common.constants.ErrorCodeConstant.PREFIX_ERROR_CODE_GENERIC;

import java.util.Locale;
import lombok.Getter;

public enum GenericErrorCodeEnum implements ErrorCode {
    
    UNHANDLED(PREFIX_ERROR_CODE_GENERIC + "0001", "Unhandled exception"),
    BAD_REQUEST(PREFIX_ERROR_CODE_GENERIC + "0002", "Bad request"),
    REQUEST_VALIDATION(PREFIX_ERROR_CODE_GENERIC + "0003", "Invalid Request"),
    VERSION_MISMATCH(PREFIX_ERROR_CODE_GENERIC + "0004", "Record already updated by others"),
    TECHNICAL_EXCEPTION(PREFIX_ERROR_CODE_GENERIC + "0005", "Technical exception, please contact service owner"),
    LAST_PROCESS(PREFIX_ERROR_CODE_GENERIC + "0006", "No next step for COMPLETED process");
    
    private static final String GENERIC_BIZ_TOOLS = "GENERIC-BIZ-TOOLS";
    
    @Getter
    private final String description;
    private final String code;
    
    GenericErrorCodeEnum(String code, String description) {
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
        return GENERIC_BIZ_TOOLS;
    }
}
