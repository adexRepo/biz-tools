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
public class GenericBizException extends BaseException {
    
    @Serial
    private static final long serialVersionUID = 8148718964712761444L;
    
    @Builder(builderMethodName = "construct")
    public GenericBizException(ErrorCode errorCode, String errorMessage,
            ErrorInfo errorInfo, Locale locale, Throwable cause) {
        super(isNull(errorCode) ? BaseExceptionEnum.BAD_REQUEST : errorCode, errorMessage,
                errorInfo, locale, cause, null);
    }
    
    public GenericBizException(@NonNull ErrorCode errorCode) {
        super(errorCode);
    }
    
    public GenericBizException(@NonNull ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public GenericBizException(@NonNull ErrorCode errorCode, Object[] params) {
        super(errorCode, params);
    }
    
    public GenericBizException(@NonNull ErrorCode errorCode, String errorMessage,
            ErrorInfo errorInfo) {
        super(errorCode, errorMessage, errorInfo);
    }
}
