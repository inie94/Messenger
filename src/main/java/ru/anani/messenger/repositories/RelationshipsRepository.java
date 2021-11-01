package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Relationship;
import ru.anani.messenger.entities.User;

import java.util.List;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationship, Long> {

//    @Query("SELECT r FROM Relationship r INNER JOIN Topic t ON t = r.topic " +
//            "WHERE t.mode = ?3 AND (r.user = ?1 OR r.user = ?2) " +
//            "AND r.topic IN (SELECT topic FROM Relationship GROUP BY topic HAVING COUNT(topic) > 1)")
    List<Relationship> findAllByUser(User user);


}
