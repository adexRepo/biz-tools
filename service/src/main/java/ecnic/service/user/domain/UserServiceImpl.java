package ecnic.service.user.domain;

import static ecnic.service.common.security.PasswordSecurity.checkPasswordsMatch;

import ecnic.service.common.exceptions.GenericBizException;
import ecnic.service.common.models.PagedResult;
import ecnic.service.common.security.PasswordSecurity;
import ecnic.service.common.security.UserCredential;
import ecnic.service.common.security.jwt.JwtService;
import ecnic.service.user.UserService;
import ecnic.service.user.constants.UserErrorEnum;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    
    @Override
    public UserCredential findUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return UserMapper.INSTANCE.userEntityToUserCredential(userEntity);
    }
    
    @Override
    public UserCredential login(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username);
        
        if (Objects.isNull(userEntity)) {
            throw new GenericBizException(UserErrorEnum.USER_NOT_FOUND,
                    String.format(UserErrorEnum.USER_NOT_FOUND.getDescription(), username));
        }
        
        // future need to verify device permitted
        boolean isCorrectPassword = checkPasswordsMatch(password, userEntity.getPassword(),
                userEntity.getSaltPassword());
        
        if (!isCorrectPassword) {
            throw new GenericBizException(UserErrorEnum.USERNAME_AND_PASSWORD_NOT_MATCH,
                    String.format(UserErrorEnum.USERNAME_AND_PASSWORD_NOT_MATCH.getDescription(),
                            username));
        }
        
        String token = jwtService.generateToken(userEntity.getUsername());
        return UserMapper.INSTANCE.userEntityToUserCredentialAndToken(userEntity, token);
    }
    
    @Override
    public List<User> createUser(List<CreateUser> createUser) {
        List<UserEntity> newUserEntities =
                UserMapper.INSTANCE.createUsersToEntities(createUser);
        
        for (UserEntity entity : newUserEntities) {
            try {
                String saltPassword = String.valueOf(Instant.now().toEpochMilli());
                String encryptedPassword = PasswordSecurity.encryptPassword(entity.getPassword(),
                        saltPassword);
                entity.setSaltPassword(saltPassword);
                entity.setPassword(encryptedPassword);
            } catch (NoSuchAlgorithmException nsae) {
                log.error("[User Module] createUser {} failed");
            }
        }
        List<UserEntity> userEntities = userRepository.saveAllAndFlush(newUserEntities);
        return UserMapper.INSTANCE.entitiesToUsers(userEntities);
    }
    
    
    @Override
    public PagedResult<User> getUsers(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        List<User> users = UserMapper.INSTANCE.entitiesToUsers(page.getContent());
        
        return new PagedResult<>(
                users,
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(), page.isLast(), page.hasNext(), page.hasPrevious()
        );
    }
    
    @Override
    public User getUser(UUID id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new GenericBizException(UserErrorEnum.USER_NOT_FOUND,
                    String.format(UserErrorEnum.USER_NOT_FOUND.getDescription(), id));
        }
        return UserMapper.INSTANCE.entityToUser(userEntityOptional.get());
    }
    
    @Override
    public List<User> updateUser(List<UpdateUser> updateUsers) {
        List<UUID> ids = updateUsers.stream().map(UpdateUser::id).toList();
        List<UserEntity> userEntities = userRepository.findAllById(ids);
        if (userEntities.isEmpty()) {
            throw new GenericBizException(UserErrorEnum.USER_NOT_FOUND,
                    String.format(UserErrorEnum.USER_NOT_FOUND.getDescription(), ids));
        }
        
        UserMapperCustom.updateEntity(userEntities, updateUsers);
        userRepository.saveAll(userEntities);
        return UserMapper.INSTANCE.entitiesToUsers(userEntities);
    }
    
    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
