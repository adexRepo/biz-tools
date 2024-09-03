package ecnic.service.user.domain;

import static ecnic.service.config.security.PasswordSecurity.checkPasswordsMatch;
import static ecnic.service.user.domain.UserMapper.convertAddAccessToken;
import static ecnic.service.user.domain.UserMapper.convertToUserCredential;

import ecnic.service.common.exceptions.GenericBizException;
import ecnic.service.common.models.PagedResult;
import ecnic.service.config.security.jwt.JwtService;
import ecnic.service.user.UserService;
import ecnic.service.user.constants.UserErrorEnum;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import ecnic.service.user.domain.models.UserCredential;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    
    @Override
    public UserCredential findUsername(String username) {
        UserEntity userentity = userRepository.findByUsername(username);
        return convertToUserCredential(userentity);
    }
    
    @Override
    public UserCredential login(String username, String password)  {
        UserEntity userEntity = userRepository.findByUsername(username);
        
        if (Objects.isNull(userEntity)) {
            throw new GenericBizException(UserErrorEnum.USER_NOT_FOUND,
                    String.format(UserErrorEnum.USER_NOT_FOUND.getDescription(), username));
        }
        
        // future need to verify device ipv4
        boolean isCorrectPassword = checkPasswordsMatch(password, userEntity.getPassword(),
                userEntity.getSaltPassword());
        
        if (!isCorrectPassword) {
            throw new GenericBizException(UserErrorEnum.USERNAME_AND_PASSWORD_NOT_MATCH,
                    String.format(UserErrorEnum.USERNAME_AND_PASSWORD_NOT_MATCH.getDescription(),
                            username));
        }
        
        UserCredential userCredential = convertToUserCredential(userEntity);
        String token = jwtService.generateToken(userCredential);
        return convertAddAccessToken(userCredential, token);
    }
    
    @Override
    public List<User> createUser(List<CreateUser> createUser) {
        List<UserEntity> userEntity = UserMapper.convertToEntity(createUser);
        userEntity = userRepository.saveAllAndFlush(userEntity);
        return UserMapper.convertToUser(userEntity);
    }
    
    @Override
    public PagedResult<User> getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAllBy(pageable);
        return new PagedResult<>(usersPage);
    }
    
    @Override
    public User getUser(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new GenericBizException(UserErrorEnum.USER_NOT_FOUND,
                    String.format(UserErrorEnum.USER_NOT_FOUND.getDescription(), id));
        }
        return UserMapper.convertToUser(userEntityOptional.get());
    }
    
    @Override
    public List<User> updateUser(List<UpdateUser> updateUsers) {
        List<Long> ids = updateUsers.stream().map(UpdateUser::id).toList();
        List<UserEntity> userEntities = userRepository.findAllById(ids);
        if (userEntities.isEmpty()) {
            throw new GenericBizException(UserErrorEnum.USER_NOT_FOUND,
                    String.format(UserErrorEnum.USER_NOT_FOUND.getDescription(), ids));
        }
        
        UserMapper.convertToEntity(userEntities, updateUsers);
        userRepository.saveAll(userEntities);
        return UserMapper.convertToUser(userEntities);
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
