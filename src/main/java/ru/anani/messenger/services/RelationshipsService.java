package ru.anani.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.anani.messenger.repositories.RelationshipsRepository;

@Service
public class RelationshipsService {

    private final RelationshipsRepository relationshipsRepository;

    @Autowired
    public RelationshipsService(RelationshipsRepository relationshipsRepository) {
        this.relationshipsRepository = relationshipsRepository;
    }

//    public Relationship save(Relationship relationship) {
//        return relationshipsRepository.save(relationship);
//    }
//
//    public List<Relationship> getRelationshipByUserAndUserAndTopicStatus(User user, User companion, TopicMode topicMode) {
//        return relationshipsRepository.findAllByUserOrCompanionAndTopicMode(user, companion, topicMode);
//    }
//
//    public Relationship getById(Long id) {
//        return relationshipsRepository.findById(id).get();
//    }
}
