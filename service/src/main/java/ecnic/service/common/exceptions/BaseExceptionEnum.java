package ecnic.service.common.exceptions;

import static ecnic.service.common.constants.ErrorCodeConstant.PREFIX_ERROR_CODE_GENERIC;

import java.util.Locale;
import lombok.Getter;

public enum BaseExceptionEnum implements ErrorCode {
    
    UNHANDLED(PREFIX_ERROR_CODE_GENERIC + "0001", "Unhandled exception"),
    BAD_REQUEST(PREFIX_ERROR_CODE_GENERIC + "0002", "Bad request"),
    INPUT_FIELD_VALIDATION(PREFIX_ERROR_CODE_GENERIC + "0003", "Input Field Validation Field"),
    VERSION_MISMATCH(PREFIX_ERROR_CODE_GENERIC + "0004", "Record already updated by others"),
    TECHNICAL(PREFIX_ERROR_CODE_GENERIC + "0005", "Technical exception, please contact service owner"),
    LAST_PROCESS(PREFIX_ERROR_CODE_GENERIC + "0006", "No next step for COMPLETED process"),
    ACCESS_DENIED(PREFIX_ERROR_CODE_GENERIC + "0007", "Access denied"),
    SERVICE_DOWN(PREFIX_ERROR_CODE_GENERIC + "0008", "Service unavailable"),
    METHOD_ARGUMENT_TYPE_NOT_MATCH(PREFIX_ERROR_CODE_GENERIC + "0009", "Method argument type not match"),
    METHOD_ARGUMENT_TYPE_NOT_VALID(PREFIX_ERROR_CODE_GENERIC + "0010", "Method argument type not valid"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(PREFIX_ERROR_CODE_GENERIC + "0011", "Method argument type not valid");
    
    private static final String GENERIC_BIZ_TOOLS = "GENERIC-BIZ-TOOLS";
    
    @Getter
    private final String description;
    private final String code;
    
    BaseExceptionEnum(String code, String description) {
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
