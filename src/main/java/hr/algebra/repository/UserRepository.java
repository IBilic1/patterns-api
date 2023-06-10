package hr.algebra.repository;

import hr.algebra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.password = ?2")
    User findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> existsByEmail(String email);
}
