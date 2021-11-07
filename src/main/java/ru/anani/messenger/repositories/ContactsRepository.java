package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Contact;
import ru.anani.messenger.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long> {

    boolean existsByUserAndCompanion(User user, User companion);

    void deleteByUserAndCompanion(User user, User companion);

    List<Contact> findAllByUser(User user);

    Optional<Contact> findByUserAndCompanion(User user, User companion);
}
