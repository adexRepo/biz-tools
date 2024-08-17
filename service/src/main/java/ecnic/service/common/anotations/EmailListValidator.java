package ecnic.service.common.anotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailListValidator implements
    ConstraintValidator<ValidEmailList, List<String>> {

  @Override
  public void initialize(ValidEmailList constraint) {
    log.debug("initialize Email Validator");
  }

  @Override
  public boolean isValid(List<String> emails, ConstraintValidatorContext context) {
    if (emails == null) {
      return true; // null values are validated by @NotNull or @NotBlank
    }

    for (String email : emails) {
      if (!isValidEmail(email)) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidEmail(String email) {
    // Implement your email validation logic (e.g., using regex or Apache Commons Validator)
    return email != null && email.matches("your_email_validation_regex_here");
  }
}
