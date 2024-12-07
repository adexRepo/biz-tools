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
public class GenericBadException extends BaseException {
    
    @Serial
    private static final long serialVersionUID = -7829589115064726036L;
    
    @Builder(builderMethodName = "construct")
    public GenericBadException(ErrorCode errorCode, String errorMessage,
            ErrorInfo errorInfo, Locale locale, Throwable cause) {
        super(isNull(errorCode) ? BaseExceptionEnum.BAD_REQUEST : errorCode, errorMessage,
                errorInfo, locale, cause, null);
    }
    
    public GenericBadException(@NonNull ErrorCode errorCode) {
        super(errorCode);
    }
    
    public GenericBadException(@NonNull ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public GenericBadException(@NonNull ErrorCode errorCode, String errorMessage,
            ErrorInfo errorInfo) {
        super(errorCode, errorMessage, errorInfo);
    }
}
