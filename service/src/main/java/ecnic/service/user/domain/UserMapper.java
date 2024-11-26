package ecnic.service.user.domain;

import ecnic.service.common.security.UserCredential;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.CreateUserDTO;
import ecnic.service.user.domain.models.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    UserEntity toEntity(CreateUserDTO createUsers);
    
    User toRecordUser(CreateUserDTO createUsers);

    List<User> entitiesToUsers(List<UserEntity> entities);
    
    UserCredential userEntityToUserCredential(UserEntity userEntity);
    
    UserCredential userEntityToUserCredentialAndToken(UserEntity userEntity, String token);
    
    User entityToUser(UserEntity userEntity);
    
}
