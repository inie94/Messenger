package ru.anani.messenger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.services.ContactsService;
import ru.anani.messenger.services.DialogService;
import ru.anani.messenger.services.UserService;

@SpringBootTest
class MessengerApplicationTests {

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	@Autowired
	public ContactsService contactsService;
	@Autowired
	public UserService userService;
	@Autowired
	public DialogService dialogService;

	@Test
	void passwords() {
		System.out.println("jjellico0@fema.gov: " + passwordEncoder.encode("iM3M0VMjT"));
		System.out.println("gavramovsky3@miitbeian.gov.cn: " + passwordEncoder.encode("aTb9TNZte"));
		System.out.println("rholdron5@gnu.org: " + passwordEncoder.encode("Bvbl6M9gK"));
		System.out.println("ztrood4@meetup.com: " + passwordEncoder.encode("1Y9dBxpyqpi"));
		System.out.println("ltuddall6@shutterfly.com: " + passwordEncoder.encode("lBWljv"));
		System.out.println("acurrin7@wp.com: " + passwordEncoder.encode("iaR6aTBTyco4"));
		System.out.println("mcornewell8@blog.com: " + passwordEncoder.encode("nt5zgKjsoVn"));
		System.out.println("dkelf9@github.io: " + passwordEncoder.encode("FRwR5TxW"));
		System.out.println("cbuseka@ed.gov: " + passwordEncoder.encode("otqImF"));
		System.out.println("jskewisb@go.com: " + passwordEncoder.encode("1w3oU8xU"));
	}

	@Test
	public void saveContact() {
//		User user = userService.findById(1);
//		User companion = userService.findById(2);
//
//		Contact activeContact = new Contact();
//		activeContact.setUser(user);
//		activeContact.setContact(companion);
//		ru.anani.messenger.entities.abstractClasses.Contact contact = contactsService.save(activeContact);
//		System.out.println(contact);
	}

	@Test
	public void saveContactToBlockList() {
//		User user = userService.findById(1);
//		User companion = userService.findById(2);
//
//		BlockedUser blockedContact = new BlockedUser();
//		blockedContact.setUser(user);
//		blockedContact.setContact(companion);
//		ru.anani.messenger.entities.abstractClasses.Contact contact = contactsService.save(blockedContact);
//		System.out.println(contact);
	}

	@Test
	public void dialogCreate() {
		User userA = userService.findById(1);
		User userB = userService.findById(2);

		dialogService.create(userA, userB);
	}

	@Test
	public void dialogHideToUser() {
		User user = userService.findById(1);
		Dialog dialog = dialogService.getDialogById(1L);
		dialogService.hideToUser(dialog, user);
	}

	@Test
	public void dialogShowToUser() {
		User user = userService.findById(1);
		Dialog dialog = dialogService.getDialogById(1L);
		dialogService.showToUser(dialog, user);
	}
}
