package ecnic.service.common.advice;

import static ecnic.service.common.constants.SystemConstant.SYSTEM_LOCALE;
import static ecnic.service.common.exceptions.BaseExceptionEnum.BAD_REQUEST;
import static ecnic.service.common.exceptions.BaseExceptionEnum.METHOD_ARGUMENT_TYPE_NOT_MATCH;
import static ecnic.service.common.exceptions.BaseExceptionEnum.METHOD_ARGUMENT_TYPE_NOT_VALID;
import static ecnic.service.common.exceptions.BaseExceptionEnum.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION;
import static ecnic.service.common.exceptions.BaseExceptionEnum.TECHNICAL;
import static ecnic.service.common.exceptions.BaseExceptionEnum.UNHANDLED;

import ecnic.service.common.exceptions.BaseException;
import ecnic.service.common.exceptions.BaseExceptionEnum;
import ecnic.service.common.exceptions.ErrorInfo;
import ecnic.service.common.exceptions.ErrorInfo.ErrorDetail;
import ecnic.service.common.exceptions.ErrorInfo.ErrorProvider;
import ecnic.service.common.exceptions.ErrorResponse;
import ecnic.service.common.exceptions.GenericAccessDeniedException;
import ecnic.service.common.exceptions.GenericBadException;
import ecnic.service.common.exceptions.GenericBizException;
import ecnic.service.common.exceptions.GenericException;
import ecnic.service.common.exceptions.GenericServiceUnavailableException;
import ecnic.service.common.models.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
public class BaseControllerAdviceHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GenericBadException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleGenericBadException(
            HttpServletRequest httpServletRequest, BaseException baseException) {
        
        printLogError("handleGenericBadException", baseException);
        printURIAndParameter(httpServletRequest);
        
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("GENERIC_BAD_REQUEST_EXCEPTION",
                null, null, errorResponse);
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleMethodArgumentTypeMisMatchException(
            HttpServletRequest httpServletRequest) {
        
        log.error("[handleMethodArgumentTypeMisMatchException] Type Mismatch Exception");
        
        printURIAndParameter(httpServletRequest);
        GenericBadException genericBadException = new GenericBadException(
                METHOD_ARGUMENT_TYPE_NOT_MATCH,
                METHOD_ARGUMENT_TYPE_NOT_MATCH.getDescription(SYSTEM_LOCALE));
        ErrorResponse errorResponse = new ErrorResponse(genericBadException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("METHOD_ARGUMENT_TYPE_NOT_MATCH",
                null, null, errorResponse);
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenericException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleGenericException(
            HttpServletRequest httpServletRequest, BaseException baseException) {
        
        printLogError("handleGenericException", baseException);
        printURIAndParameter(httpServletRequest);
        
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("GENERIC_EXCEPTION",
                null, null, errorResponse);
        
        return ResponseEntity.internalServerError().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(GenericBizException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleGenericBizException(
            HttpServletRequest httpServletRequest, BaseException baseException) {
        
        printLogError("handleGenericBizException", baseException);
        printURIAndParameter(httpServletRequest);
        
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("FAILED_BUSINESS_REQUEST",
                null, null, errorResponse);
        
        return ResponseEntity.ok().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(GenericAccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleGenericAccessDeniedException(
            HttpServletRequest httpServletRequest, BaseException baseException) {
        
        printLogError("handleGenericAccessDeniedException", baseException);
        printURIAndParameter(httpServletRequest);
        
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("GENERIC_ACCESS_DENIED_EXCEPTION",
                null, null, errorResponse);
        
        return ResponseEntity.ok().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(GenericServiceUnavailableException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleServiceUnavailableException(
            HttpServletRequest httpServletRequest, BaseException baseException) {
        
        printLogError("handleServiceUnavailableException", baseException);
        printURIAndParameter(httpServletRequest);
        
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("FAILED_PROCESS",
                null, null, errorResponse);
        
        return ResponseEntity.status(503).body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleException(
            HttpServletRequest httpServletRequest,
            Exception exception) {
        
        BaseException baseException =
                buildBaseException(exception.getCause(),
                        UNHANDLED, "Unhandled Exception", null);
        printLogError("handleException", baseException);
        printURIAndParameter(httpServletRequest);
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("UNHANDLED_EXCEPTION",
                null, null, errorResponse);
        
        return ResponseEntity.internalServerError().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleThrowable(
            HttpServletRequest httpServletRequest,
            Exception exception) {
        
        BaseException baseException =
                buildBaseException(exception.getCause(), TECHNICAL,
                        "Unhandled Throwable Exception", null);
        
        printURIAndParameter(httpServletRequest);
        printLogError("handleThrowable", baseException);
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>("UNHANDLED_THROWABLE_EXCEPTION",
                null, null, errorResponse);
        
        return ResponseEntity.internalServerError().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleHttpMessageNotReadableException(
            HttpServletRequest httpServletRequest,
            Exception exception) {
        
        BaseException baseException =
                buildBaseException(exception.getCause(), BAD_REQUEST,
                        "Http Message Not Readable", null);
        
        printURIAndParameter(httpServletRequest);
        printLogError("handleHttpMessageNotReadableException", baseException);
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>(
                "HTTP_MESSAGE_NOT_READABLE_EXCEPTION", null, null, errorResponse);
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleMethodArgumentNotValidException(
            HttpServletRequest httpServletRequest,
            MethodArgumentNotValidException exception) {
        
        List<ErrorProvider> errorProviders = new ArrayList<>();
        
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorProviders.add(
                    ErrorProvider.builder()
                            .code(error.getField())
                            .detail(error.getDefaultMessage())
                            .build()
            );
        }
        
        for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            errorProviders.add(
                    ErrorProvider.builder()
                            .code(error.getObjectName())
                            .detail(error.getDefaultMessage())
                            .build()
            );
        }
        
        ErrorInfo errorInfo = ErrorInfo.builder()
                .errorDetails(List.of(ErrorDetail.builder()
                        .errorProviders(errorProviders)
                        .build()))
                .build();
        
        BaseException baseException = buildBaseException(exception,
                METHOD_ARGUMENT_TYPE_NOT_VALID,
                METHOD_ARGUMENT_TYPE_NOT_VALID.getDescription(SYSTEM_LOCALE),
                errorInfo
        );
        
        printLogError("handleHttpMessageNotReadableException", baseException);
        printURIAndParameter(httpServletRequest);
        
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>(
                "METHOD_ARGUMENT_NOT_VALID_EXCEPTION", null, null, errorResponse);
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Serializable>> handleMissingServletRequestParameterException(
            HttpServletRequest httpServletRequest,
            MissingServletRequestParameterException exception) {
        
        BaseException baseException =
                buildBaseException(exception.getCause(),
                        MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION,
                        exception.getMessage(), null);
        
        printURIAndParameter(httpServletRequest);
        printLogError("handleMissingServletRequestParameterException", baseException);
        
        ErrorResponse errorResponse = new ErrorResponse(baseException);
        
        ApiResponse<Serializable> apiResponse = new ApiResponse<>(
                "MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION", null, null, errorResponse);
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    protected BaseException buildBaseException(Throwable cause,
            BaseExceptionEnum exceptionEnum,
            String errorMessage,
            ErrorInfo errorInfo
    ) {
        return BaseException.create()
                .errorCode(exceptionEnum).cause(cause)
                .errorMessage(errorMessage)
                .errorInfo(errorInfo)
                .build();
    }
    
    protected void printLogError(String handlerName, BaseException baseException) {
        log.error("[{}] errorCode:{}, errorCode:{}", handlerName, baseException.getErrorCode(),
                baseException.getErrorCode().getDescription(SYSTEM_LOCALE));
    }
    
    protected void printURIAndParameter(HttpServletRequest httpServletRequest) {
        StringJoiner strJoiner = new StringJoiner(",");
        httpServletRequest.getParameterMap()
                .forEach((key, value) -> strJoiner.add(key + ":" + String.join(",", value)));
        log.error("[printURIAndParameter] Request URI {}, Request parameter : {}",
                httpServletRequest.getRequestURI(), strJoiner.toString());
    }
    
}
