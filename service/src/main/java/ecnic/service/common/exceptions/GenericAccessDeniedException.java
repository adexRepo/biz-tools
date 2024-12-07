package ecnic.service.common.exceptions;

import static java.util.Objects.isNull;

import java.io.Serial;
import java.util.Locale;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class GenericAccessDeniedException extends BaseException {
    
    @Serial
    private static final long serialVersionUID = -7829589115064726036L;
    
    @Builder(builderMethodName = "construct")
    public GenericAccessDeniedException(ErrorCode errorCode, String errorMessage,
            ErrorInfo errorInfo, Locale locale, Throwable cause) {
        super(isNull(errorCode) ? BaseExceptionEnum.ACCESS_DENIED : errorCode, errorMessage,
                errorInfo, locale, cause, null);
    }
    
    public GenericAccessDeniedException(@NonNull ErrorCode errorCode) {
        super(errorCode);
    }
    
    public GenericAccessDeniedException(@NonNull ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public GenericAccessDeniedException(@NonNull ErrorCode errorCode, String errorMessage,
            ErrorInfo errorInfo) {
        super(errorCode, errorMessage, errorInfo);
    }
}