package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.Message;
import ru.anani.messenger.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE createdBy IN " +
            "(SELECT MAX(createdBy) FROM Message m " +
            "WHERE m.dialog = :dialog)")
    Optional<Message> getLastMessageByDialog(@Param("dialog") Dialog dialog);
//
    List<Message> findFirst20ByDialogOrderByCreatedByDesc(Dialog dialog);
//
    @Query("SELECT COUNT(m) FROM Message m WHERE m.dialog = :dialog AND m.sender != :user AND m.status != 2")
    Long getCountOfNewMessagesInDialogWhereSenderNotUser(@Param("dialog") Dialog dialog, @Param("user") User user);
//
//    @Query("SELECT m FROM Message m WHERE m.status != 2 AND m.recipient = :recipient")
//    List<Message> findAllMessagesIsNotReadByUser(@Param("recipient") User user);
}
