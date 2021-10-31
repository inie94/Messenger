package ru.anani.messenger.repositories;

//import org.aspectj.asm.internal.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Relationship;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationship, Long> {

//    @Query("SELECT r FROM Relationship r INNER JOIN Topic t ON t = r.topic " +
//            "WHERE t.mode = ?3 AND (r.user = ?1 OR r.user = ?2) " +
//            "AND r.topic IN (SELECT topic FROM Relationship GROUP BY topic HAVING COUNT(topic) > 1)")
//    List<Relationship> findAllByUserOrCompanionAndTopicMode(User user, User companion, TopicMode mode);


}
