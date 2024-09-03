package ecnic.service.user;

import ecnic.service.common.models.PagedResult;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import ecnic.service.user.domain.models.UserCredential;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    UserCredential findUsername(String username);
    
    UserCredential login(String username, String password);
    
    PagedResult<User> getUsers(Pageable pageable);
    
    User getUser(Long id);
    
    List<User> createUser(List<CreateUser> createUsers);
    
    List<User> updateUser(List<UpdateUser> updateUsers);
    
    void deleteUser(Long ids);
    
}
