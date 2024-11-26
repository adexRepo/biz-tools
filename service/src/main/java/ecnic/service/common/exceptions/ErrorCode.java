package ecnic.service.common.exceptions;

import java.io.Serializable;
import java.util.Locale;

public interface ErrorCode extends Serializable {
    
    String getLocalCode();
    
    String getDescription(Locale locale);
    
    String getModuleCode();
    
    default String getCode() {
        String defaultCode = this.getModuleCode();
        return defaultCode + "-" + this.getLocalCode();
    }
    
}
