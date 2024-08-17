package ecnic.service.user.domain.exception;

import java.util.List;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }

  public static UserNotFoundException forUserId(Long userId) {
    return new UserNotFoundException("User with ID Number " + userId + " not found");
  }

  public static UserNotFoundException forUserIds(List<Long> userIds) {
    return new UserNotFoundException(
        "All User with ID Number " + userIds.toString() + " not found");
  }
}
