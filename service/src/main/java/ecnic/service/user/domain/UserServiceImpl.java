package ecnic.service.user.domain;

import static ecnic.service.user.domain.UserMapper.convertToEntity;
import static ecnic.service.user.domain.UserMapper.convertToUser;

import ecnic.service.common.models.PagedResult;
import ecnic.service.user.UserService;
import ecnic.service.user.domain.exception.UserNotFoundException;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import java.util.List;
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

  @Override
  public PagedResult<User> getUsers(Pageable pageable) {
    Page<User> usersPage = userRepository.findAllBy(pageable);
    return new PagedResult<>(usersPage);
  }

  @Override
  public User getUser(Long id) {
    Optional<UserEntity> userEntityOptional = userRepository.findById(id);
    if (userEntityOptional.isEmpty()) {
      throw UserNotFoundException.forUserId(id);
    }
    return UserMapper.convertToUser(userEntityOptional.get());
  }

  @Override
  public List<User> createUser(List<CreateUser> createUser) {
    List<UserEntity> userEntity = UserMapper.convertToEntity(createUser);
    userEntity = userRepository.saveAllAndFlush(userEntity);
    return UserMapper.convertToUser(userEntity);
  }

  @Override
  public List<User> updateUser(List<UpdateUser> updateUsers) {
    List<Long> ids = updateUsers.stream().map(UpdateUser::id).toList();
    List<UserEntity> userEntities = userRepository.findAllById(ids);
    if (userEntities.isEmpty()) {
      throw UserNotFoundException.forUserIds(ids);
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
