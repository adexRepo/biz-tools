package ecnic.service.common.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ToString
public class ErrorInfo implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 8714222897430577359L;
    
    private List<ErrorDetail> errorDetails;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(Include.NON_NULL)
    public static class ErrorDetail implements Serializable {
        
        @Serial
        private static final long serialVersionUID = 1449810678369570224L;
        
        protected String type;
        protected String code;
        protected String status;
        protected String description;
        protected String actor;
        protected transient Object[] params;
        private List<ErrorProvider> errorProviders;
        
        public ErrorDetail addProviderError(ErrorProvider errorProvider) {
            List<ErrorProvider> providerErrors = getErrorProviders();
            if (!providerErrors.isEmpty()) {
                providerErrors = new ArrayList<>();
            }
            
            providerErrors.add(errorProvider);
            return this;
        }
    }
    
    private List<ErrorProvider> errorProviders;
    
    public List<ErrorProvider> getErrorProvider() {
        return errorProviders;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(Include.NON_NULL)
    @ToString
    public static class ErrorProvider implements Serializable {
        
        @Serial
        private static final long serialVersionUID = -3504466472605741293L;
        
        protected String code;
        protected String detail;
        
    }
    
}
