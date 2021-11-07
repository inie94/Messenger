package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.User;

import java.util.List;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    boolean existsByUserAAndUserBOrUserAAndUserB(User userA, User userB, User userB1, User userA1);

    Dialog findByUserAAndUserBOrUserAAndUserB(User userA, User userB, User userB1, User userA1);

    List<Dialog> findAllByUserAAndUserBOrUserAAndUserB(User userA, User userB, User userB1, User userA1);

    List<Dialog> findAllByUserAOrUserB(User user, User user1);
}
