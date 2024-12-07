package ecnic.service.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ecnic.service.common.exceptions.ErrorResponse;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -3104853712623535477L;
    
    private String status;
    private transient T response;
    private transient List<T> responseList;
    private transient ErrorResponse errorResponse;
    
    
}
