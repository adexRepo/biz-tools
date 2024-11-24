package ecnic.service.user.domain;

import ecnic.service.user.domain.models.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
interface UserRepository extends JpaRepository<UserEntity, UUID> {
  
  UserEntity findByUsername(String username);

}
