package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.BlockedUser;
import ru.anani.messenger.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockedUsersRepository extends JpaRepository<BlockedUser, Long> {

    boolean existsByUserAndCompanion(User user, User contact);

    void deleteByUserAndCompanion(User user, User contact);

    List<BlockedUser> findAllByUser(User user);

    Optional<BlockedUser> findByUserAndCompanion(User user, User contact);

    List<BlockedUser> findAllByCompanion(User authorizedUser);
}
