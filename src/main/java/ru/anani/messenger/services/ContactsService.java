package ru.anani.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anani.messenger.entities.Contact;
import ru.anani.messenger.entities.BlockedUser;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.repositories.ContactsRepository;
import ru.anani.messenger.repositories.BlockedUsersRepository;

import java.util.List;

@Service
@Transactional
public class ContactsService {

    private final ContactsRepository contactsRepository;
    private final BlockedUsersRepository blockedUsersRepository;

    @Autowired
    public ContactsService(ContactsRepository contactsRepository, BlockedUsersRepository blockedUsersRepository) {
        this.contactsRepository = contactsRepository;
        this.blockedUsersRepository = blockedUsersRepository;
    }

    public Contact addToContactList(User user, User companion) {
        return contactsRepository.save(new Contact(user, companion));
    }

    public BlockedUser addToBlockList(User user, User companion) {
        if (contactsRepository.existsByUserAndCompanion(user, companion)) {
            contactsRepository.deleteByUserAndCompanion(user, companion);
        }
        if(blockedUsersRepository.existsByUserAndCompanion(user, companion)) {
            return blockedUsersRepository.findByUserAndCompanion(user, companion).get();
        } else {
            return blockedUsersRepository.save(new BlockedUser(user, companion));
        }
    }

    public List<Contact> getContactsByUser(User user) {
        return contactsRepository.findAllByUser(user);
    }

    public List<BlockedUser> getBlockedContactsByUser(User user) {
        return blockedUsersRepository.findAllByUser(user);
    }

    public void deleteContactByUserAndCompanion(User user, User companion) {
        if (contactsRepository.existsByUserAndCompanion(user, companion)) {
            contactsRepository.deleteByUserAndCompanion(user, companion);
        }
    }

    public void unblockCompanionByUser(User user, User companion) {
        if (blockedUsersRepository.existsByUserAndCompanion(user, companion)) {
            blockedUsersRepository.deleteByUserAndCompanion(user, companion);
        }
    }

}
