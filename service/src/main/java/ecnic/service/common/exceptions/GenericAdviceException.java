package ecnic.service.common.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Generic advice exception.
 */
@RestControllerAdvice
public class GenericAdviceException extends ResponseEntityExceptionHandler {
    
    private static final String TIMESTAMP = "timestamp";
    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_DESCRIPTION = "errorDescription";
    
    /**
     * Handler bad request problem detail.
     *
     * @param e the e
     * @return the problem detail
     */
    @ExceptionHandler({InvalidFormatException.class, MismatchedInputException.class})
    ProblemDetail handlerBadRequest(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid Request");
        problemDetail.setProperty(ERROR_CODE, GenericErrorCodeEnum.BAD_REQUEST);
        problemDetail.setProperty(ERROR_DESCRIPTION,
                GenericErrorCodeEnum.BAD_REQUEST.getDescription());
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
    
    /**
     * Handle exception problem detail.
     *
     * @param e the e
     * @return the problem detail
     */
    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Technical exception");
        problemDetail.setProperty(ERROR_CODE, GenericErrorCodeEnum.TECHNICAL_EXCEPTION);
        problemDetail.setProperty(ERROR_DESCRIPTION,
                GenericErrorCodeEnum.TECHNICAL_EXCEPTION.getDescription());
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
    
}
