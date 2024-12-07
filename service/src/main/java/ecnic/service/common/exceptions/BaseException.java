package ecnic.service.common.exceptions;

import java.io.Serial;
import java.util.Locale;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class BaseException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 362868483635006783L;
    
    private final ErrorCode errorCode;
    private final ErrorInfo errorInfo;
    
    @Builder(builderMethodName = "create")
    protected BaseException(@NonNull ErrorCode errorCode, String errorMessage, ErrorInfo errorInfo,
            Locale locale, Throwable cause, Object[] params) {
        super(getMessage(errorCode, errorMessage, locale, params), cause);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }
    
    protected BaseException(@NonNull ErrorCode errorCode, String errorMessage) {
        this(errorCode, errorMessage, null, null, null, null);
    }
    
    protected BaseException(@NonNull ErrorCode errorCode) {
        this(errorCode, null, null, null, null, null);
    }
    
    protected BaseException(@NonNull ErrorCode errorCode, Object[] params) {
        this(errorCode, null, null, null, null, params);
    }
    
    protected BaseException(@NonNull ErrorCode errorCode, String errorMessage, ErrorInfo errorInfo) {
        this(errorCode, errorMessage, errorInfo, null, null, null);
    }
    
    private static String getMessage(@NonNull ErrorCode errorCode, String message, Locale locale,
            Object[] params) {
        
        if (Objects.nonNull(message)) {
            return message;
        }
        
        if (Objects.isNull(locale)) {
            locale = Locale.ENGLISH;
        }
        
        String description = errorCode.getDescription(locale);
        if (Objects.nonNull(params)) {
            for (Object param : params) {
                description = description.replaceFirst("\\{}", "" + param);
            }
        }
        return description;
    }
    
}
