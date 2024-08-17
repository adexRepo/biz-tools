package ecnic.service.document.config;

import ecnic.service.common.models.ErrorType;
import ecnic.service.document.domain.exception.DocumentNotFoundException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DocumentExceptionHandlers extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DocumentNotFoundException.class)
  ProblemDetail handle(DocumentNotFoundException e) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
        e.getMessage());
    problemDetail.setTitle("Document Not Found");
    problemDetail.setProperty("errorType", ErrorType.BUSINESS);
    problemDetail.setProperty("timestamp", Instant.now());
    return problemDetail;
  }
}
