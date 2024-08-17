package ecnic.service.user.config;

import ecnic.service.common.models.ErrorType;
import ecnic.service.user.domain.exception.UserNotFoundException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class UserExceptionHandlers extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  ProblemDetail handle(UserNotFoundException e) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
        e.getMessage());
    problemDetail.setTitle("User Not Found");
    problemDetail.setProperty("errorType", ErrorType.BUSINESS);
    problemDetail.setProperty("timestamp", Instant.now());
    return problemDetail;
  }
}
