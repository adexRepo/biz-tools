package ecnic.service.common.exceptions;

import static ecnic.service.common.constants.SystemConstant.SYSTEM_LOCALE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Builder
@Data
@JsonInclude(Include.NON_NULL)
@ToString
public class ErrorResponse {
    
    private String code;
    private String description;
    private String message;
    private ErrorInfo errorInfo;
    
    public ErrorResponse(BaseException baseException) {
        this.code = baseException.getErrorCode().getCode();
        this.message = baseException.getErrorCode().getDescription(SYSTEM_LOCALE);
        this.description = baseException.getMessage();
        this.errorInfo = baseException.getErrorInfo();
    }
    
    public ErrorResponse(String code, String description, String message, ErrorInfo errorInfo) {
        this.code = code;
        this.description = description;
        this.message = message;
        this.errorInfo = errorInfo;
    }
    
}
