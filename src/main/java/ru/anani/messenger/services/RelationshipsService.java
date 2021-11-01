package ru.anani.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.anani.messenger.entities.Relationship;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.repositories.RelationshipsRepository;

import java.util.List;

@Service
public class RelationshipsService {

    private final RelationshipsRepository relationshipsRepository;

    @Autowired
    public RelationshipsService(RelationshipsRepository relationshipsRepository) {
        this.relationshipsRepository = relationshipsRepository;
    }

    public Relationship save(Relationship relationship) {
        return relationshipsRepository.save(relationship);
    }

    public List<Relationship> getRelationshipByUser(User user) {
        return relationshipsRepository.findAllByUser(user);
    }
//
//    public Relationship getById(Long id) {
//        return relationshipsRepository.findById(id).get();
//    }
}
