package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE createdBy IN " +
            "(SELECT MAX(createdBy) FROM Message m " +
            "WHERE (sender = :user AND recipient = :companion) OR " +
            "(sender = :companion AND recipient = :user))")
    Optional<Message> getLastByUserAndCompanion(@Param("user")  User user, @Param("companion") User companion);

    List<Message> findFirst20BySenderAndRecipientOrSenderAndRecipientOrderByCreatedByDesc(User user, User companion, User companion1, User user1);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.recipient = :recipient AND m.sender = :sender AND m.status != 2")
    Long getCountNewMessageCount(@Param("recipient") User recipient, @Param("sender") User sender);

    @Query("SELECT m FROM Message WHERE m.status != 2 AND m.recipient = :recipient")
    List<Message> findAllMessagesIsNotReadByUser(@Param("recipient") User user);
}
