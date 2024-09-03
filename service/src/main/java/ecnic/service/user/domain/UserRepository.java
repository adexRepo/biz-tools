package ecnic.service.user.domain;

import ecnic.service.user.domain.models.User;
import ecnic.service.user.domain.models.UserCredential;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
interface UserRepository extends JpaRepository<UserEntity, Long> {
  
  UserEntity findByUsername(String username);
  
  
  /**
   * Find all by page.
   *
   * @param pageable the pageable
   * @return the page
   */
  @Query("""
      select new ecnic.service.user.domain.models.User(
          usr.id,
          usr.firstName,
          usr.middleName,
          usr.lastName,
          usr.address,
          usr.phoneNumber,
          usr.email
      ) from UserEntity usr
      """)
  Page<User> findAllBy(Pageable pageable);
  
  
  /**
   * Find all by ids list.
   *
   * @param ids the ids
   * @return the list
   */
  @Query("""
      select new ecnic.service.user.domain.models.User(
          usr.id,
          usr.firstName,
          usr.middleName,
          usr.lastName,
          usr.address,
          usr.phoneNumber,
          usr.email
      ) from UserEntity usr
      """)
  List<User> findAllByIds(List<Long> ids);

}
