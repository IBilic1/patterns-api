package hr.algebra.repository;

import hr.algebra.model.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {

    @Query("SELECT u FROM UserPackage u WHERE u.dateTime between ?2 and ?3 and u.user.username = ?1")
    List<UserPackage> findByDateTime(String username, LocalDateTime startDateTime, LocalDateTime endDateTime);

    UserPackage findFirstByUserUsernameOrderByDateTimeAsc(String username);

    @Query(value = "SELECT u FROM UserPackage u ORDER BY u.dateTime desc")
    List<UserPackage> findAllSortedByDateTime();
}
