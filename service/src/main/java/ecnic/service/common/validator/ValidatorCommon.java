package ecnic.service.common.validator;

import static ecnic.service.common.exceptions.BaseExceptionEnum.INPUT_FIELD_VALIDATION;

import ecnic.service.common.exceptions.ErrorInfo;
import ecnic.service.common.exceptions.ErrorInfo.ErrorDetail;
import ecnic.service.common.exceptions.ErrorInfo.ErrorProvider;
import ecnic.service.common.exceptions.GenericBizException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorCommon {
    
    public static <T> void validate(T object) {
        
        ValidatorFactory validatorFactory = Validation.
                buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        List<ErrorProvider> errorProviders = new ArrayList<>();
        
        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                errorProviders.add(ErrorProvider.builder()
                        .code(violation.getPropertyPath().toString())
                        .detail(violation.getMessage())
                        .build());
            }
            buildGenericException(errorProviders);
        }
    }
    
    public static <T> void validate(T object, Class<?>... validateClasses) {
        
        ValidatorFactory validatorFactory = Validation.
                buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object, validateClasses);
        List<ErrorProvider> errorProviders = new ArrayList<>();
        
        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                errorProviders.add(ErrorProvider.builder()
                        .code(violation.getPropertyPath().toString())
                        .detail(violation.getMessage())
                        .build());
            }
            buildGenericException(errorProviders);
        }
    }
    
    private void buildGenericException(List<ErrorProvider> errorProviders) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .errorDetails(List.of(ErrorDetail.builder()
                        .errorProviders(errorProviders)
                        .build()))
                .build();
        
        throw GenericBizException.construct()
                .errorCode(INPUT_FIELD_VALIDATION)
                .errorInfo(errorInfo)
                .locale(Locale.ENGLISH)
                .errorMessage("Server side validation failed")
                .build();
    }
    
}
