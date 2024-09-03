package ecnic.service.common.exceptions;

import java.util.Locale;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.ProblemDetail;

@Getter
public class GenericBizException extends RuntimeException {
    
    private final @NonNull ErrorCode errorCode;
    private final transient ProblemDetail problemDetail;
    
    public GenericBizException(@NonNull ErrorCode errorCode, String message,
            ProblemDetail problemDetail, Locale locale, Throwable cause, Object[] params) {
        super(getMessage(errorCode, message, locale, params), cause);
        
        this.errorCode = errorCode;
        this.problemDetail = problemDetail;
    }
    
    public GenericBizException(@NonNull ErrorCode errorCode, String message) {
        this(errorCode, message, null, null, null, null);
    }
    
    public static GenericBizException wrap(Throwable exception, @NonNull ErrorCode errorCode,
            Locale local) {
        if ((exception instanceof GenericBizException gbe) &&
                !Objects.equals(errorCode, gbe.getErrorCode())) {
            return new GenericBizException(errorCode, null, gbe.getProblemDetail(), local,
                    exception, null);
        }
        return new GenericBizException(errorCode, null, null, local, exception, null);
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
