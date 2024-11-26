package ecnic.service.user.domain;

import static ecnic.service.common.security.PasswordSecurity.checkPasswordsMatch;
import static ecnic.service.user.constants.UserErrorEnum.USERNAME_AND_PASSWORD_NOT_MATCH;
import static ecnic.service.user.constants.UserErrorEnum.USER_NOT_FOUND;

import ecnic.service.common.exceptions.GenericBizException;
import ecnic.service.common.models.PagedResult;
import ecnic.service.common.security.PasswordSecurity;
import ecnic.service.common.security.UserCredential;
import ecnic.service.common.security.jwt.JwtService;
import ecnic.service.user.UserService;
import ecnic.service.user.constants.UserErrorEnum;
import ecnic.service.user.domain.models.CreateUserDTO;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import ecnic.service.user.domain.models.UserStatus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
        
        if (userEntityOptional.isEmpty()) {
            throw new GenericBizException(USER_NOT_FOUND, USER_NOT_FOUND.getDescription());
        }
        
        return UserMapper.INSTANCE.userEntityToUserCredential(userEntityOptional.get());
    }
    
    @Override
    public UserCredential login(String username, String password) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
        
        if (userEntityOptional.isEmpty()) {
            throw new GenericBizException(USER_NOT_FOUND, USER_NOT_FOUND.getDescription());
        }
        
        UserEntity userEntity = userEntityOptional.get();
        
        // future need to verify device permitted
        boolean isCorrectPassword = checkPasswordsMatch(password, userEntity.getPassword(),
                userEntity.getSaltPassword());
        
        if (!isCorrectPassword) {
            throw new GenericBizException(USERNAME_AND_PASSWORD_NOT_MATCH,
                    String.format(USERNAME_AND_PASSWORD_NOT_MATCH.getDescription(),
                            username));
        }
        
        String token = jwtService.generateToken(userEntity.getUsername());
        return UserMapper.INSTANCE.userEntityToUserCredentialAndToken(userEntity, token);
    }
    
    @Override
    public List<User> createUser(List<CreateUserDTO> createUsers) {
        
        List<User> records = new ArrayList<>();
        for (CreateUserDTO dto : createUsers) {
            
            Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(
                    dto.getUsername());
            
            if (optionalUserEntity.isPresent()) {
                dto.setUserStatus(UserStatus.USERNAME_EXIST);
                continue;
            }
            
            try {
                UserEntity entity = UserMapper.INSTANCE.toEntity(dto);
                
                String saltPassword = String.valueOf(Instant.now().toEpochMilli());
                String encryptedPassword = PasswordSecurity.encryptPassword(entity.getPassword(),
                        saltPassword);
                
                entity.setSaltPassword(saltPassword);
                entity.setPassword(encryptedPassword);
                
                dto.setUserStatus(UserStatus.DONE_CREATED);
                userRepository.saveAndFlush(entity);
            } catch (Exception e) {
                log.error("[User Module] Exception {}", e.getMessage());
                dto.setUserStatus(UserStatus.FAILED_ERROR);
            }
            User recordUser = UserMapper.INSTANCE.toRecordUser(dto);
            records.add(recordUser);
        }
        
        return records;
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
