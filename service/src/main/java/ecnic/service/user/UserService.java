package ecnic.service.user;

import ecnic.service.common.models.PagedResult;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.CreateUserDTO;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import ecnic.service.common.security.UserCredential;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    UserCredential findUsername(String username);
    
    UserCredential login(String username, String password);
    
    List<User> createUser(List<CreateUserDTO> createUsers);
    
    PagedResult<User> getUsers(Pageable pageable);
    
    User getUser(UUID id);
    
    
    List<User> updateUser(List<UpdateUser> updateUsers);
    
    void deleteUser(UUID ids);
    
}
