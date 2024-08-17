package ecnic.service.user.domain;

import ecnic.service.common.models.StatusType;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import java.util.List;
import java.util.Optional;

final class UserMapper {

  private UserMapper() {
  }

  static User convertToUser(UserEntity entity) {
    return new User(
        entity.getId(),
        entity.getFirstName(),
        entity.getMiddleName(),
        entity.getLastName(),
        entity.getAddress(),
        entity.getPhoneNumber(),
        entity.getEmail()
    );
  }

  static List<User> convertToUser(List<UserEntity> createUsers) {
    return createUsers.stream().map(UserMapper::convertToUser).toList();
  }

  static UserEntity convertToEntity(CreateUser createUser) {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(createUser.username());
    userEntity.setFirstName(createUser.firstname());
    userEntity.setMiddleName(createUser.middleName());
    userEntity.setLastName(createUser.lastName());
    userEntity.setAddress(createUser.address());
    userEntity.setCreatedBy(createUser.createdBy().toString());
    userEntity.setModifiedBy(createUser.createdBy().toString());
    userEntity.setStatus(StatusType.ACTIVE);
    userEntity.setRoleType(createUser.role());
    return userEntity;
  }

  static List<UserEntity> convertToEntity(List<CreateUser> createUsers) {
    return createUsers.stream().map(UserMapper::convertToEntity).toList();
  }

  static void convertToEntity(UserEntity userEntity, UpdateUser updatedUser) {
    userEntity.setFirstName(updatedUser.firstName());
    userEntity.setMiddleName(updatedUser.middleName());
    userEntity.setLastName(updatedUser.lastName());
    userEntity.setAddress(updatedUser.address());
    userEntity.setModifiedBy(String.valueOf(updatedUser.modifiedBy()));
  }

  static void convertToEntity(List<UserEntity> userEntities, List<UpdateUser> updatedUser) {
    for (UserEntity entity : userEntities) {
      Optional<UpdateUser> optionalUpdateUser =
          updatedUser.stream().filter(val -> val.id().equals(entity.getId())).findFirst();
      optionalUpdateUser.ifPresent(updateUser -> convertToEntity(entity, updateUser));
    }
  }
}
