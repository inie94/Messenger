package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Message;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {

//    List<Message> findFirst20ByTopicOrderByCreatedByDesc(Topic topic);

//    List<Message> findByTopicAndCreatedByAfterOrderByCreatedByAsc(Topic topic, Long timestamp);

//    List<Message> findAllByTopicAndSenderNot(Topic topic, User user);

}
