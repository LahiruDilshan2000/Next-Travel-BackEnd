package lk.ijse.identityserver.repository;

import lk.ijse.identityserver.entity.Role;
import lk.ijse.identityserver.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:46 AM on 10/21/2023
 * @project identity-server
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByNic(String nic);

    boolean existsByEmail(String email);

    Optional<User> findByNic(String string);

    void deleteByNic(String nic);

    @Query(value = "from User u where u.role = ?1")
    List<User> getUserHQLWithPageable(Role role, Pageable pageable);

    @Query(value = "from User u where u.role = ?1")
    List<User> findAll(Role role);

    @Query(value = "select u from User u where u.userName like ?1 " +
            "or u.nic like ?2 " +
            "or u.address like ?3 " +
            "or u.email like ?4 ")
    List<User> searchByText(String t1, String t2, String t3, String t4);
}
