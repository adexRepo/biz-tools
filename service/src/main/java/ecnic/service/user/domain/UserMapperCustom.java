package ecnic.service.user.domain;

import ecnic.service.user.domain.models.UpdateUser;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
final class UserMapperCustom {
    
    static void updateEntity(List<UserEntity> entities, List<UpdateUser> updateUsers) {
        entities.forEach(
                entity -> updateUsers.stream().filter(val -> entity.getId().equals(val.id()))
                        .findFirst()
                        .ifPresent(updateUser -> BeanUtils.copyProperties(entity, updateUser))
        );
    }
//
//
//    static User convertToUser(UserEntity entity) {
//        return new User(
//                entity.getId(),
//                entity.getFirstName(),
//                entity.getMiddleName(),
//                entity.getLastName(),
//                entity.getAddress(),
//                entity.getPhoneNumber(),
//                entity.getEmail()
//        );
//    }
//
//    static List<User> convertToUser(List<UserEntity> createUsers) {
//        return createUsers.stream().map(UserMapperCustom::convertToUser).toList();
//    }
//
//    static UserEntity convertToEntity(CreateUser createUser) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername(createUser.username());
//        userEntity.setFirstName(createUser.firstName());
//        userEntity.setMiddleName(createUser.middleName());
//        userEntity.setLastName(createUser.lastName());
//        userEntity.setAddress(createUser.addresses());
//        userEntity.setDeviceIds(createUser.deviceIds());
//        userEntity.setCreatedBy(createUser.createdBy());
//        userEntity.setModifiedBy(createUser.createdBy());
//        userEntity.setStatus(BaseStatus.ACTIVE);
//        userEntity.setUserRoles(createUser.userRoles());
//        return userEntity;
//    }
//
//    static List<UserEntity> convertToEntity(List<CreateUser> createUsers) {
//        return createUsers.stream().map(UserMapperCustom::convertToEntity).toList();
//    }
//
//    static void convertToEntity(UserEntity userEntity, UpdateUser updatedUser) {
//        userEntity.setFirstName(updatedUser.firstName());
//        userEntity.setMiddleName(updatedUser.middleName());
//        userEntity.setLastName(updatedUser.lastName());
//        userEntity.setAddress(updatedUser.address());
//        userEntity.setModifiedBy(String.valueOf(updatedUser.modifiedBy()));
//    }
//
//    static void convertToEntity(List<UserEntity> userEntities, List<UpdateUser> updatedUser) {
//        for (UserEntity entity : userEntities) {
//            Optional<UpdateUser> optionalUpdateUser =
//                    updatedUser.stream().filter(val -> val.id().equals(entity.getId())).findFirst();
//            optionalUpdateUser.ifPresent(updateUser -> convertToEntity(entity, updateUser)).;
//        }
//    }
//
//    static UserCredential convertToUserCredential(UserEntity entity) {
//        return new UserCredential(
//                entity.getUsername(),
//                entity.getFirstName(),
//                entity.getLastName(),
//                entity.getPassword(),
//                null,
//                new HashSet<>(entity.getUserRoles())
//        );
//    }
//
//    static UserCredential convertAddAccessToken(UserCredential userCredential, String token) {
//        return new UserCredential(
//                userCredential.username(),
//                userCredential.firstName(),
//                userCredential.lastName(),
//                userCredential.password(),
//                token,
//                userCredential.roles()
//        );
//    }
}
