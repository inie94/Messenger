package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    //SELECT * FROM users WHERE LOWER(email) LIKE LOWER('%n%') OR LOWER(first_name) LIKE LOWER('%n%') OR LOWER(last_name) LIKE LOWER('%n%');
    @Query("SELECT u FROM User u " +
            "WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
                  "LOWER(u.firstname) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
                  "LOWER(u.lastname) LIKE LOWER(CONCAT('%', :value, '%'))")
    List<User> findAllByEmailOrFirstnameOrLastnameContainsIgnoreCase(@Param("value") String value);

}
