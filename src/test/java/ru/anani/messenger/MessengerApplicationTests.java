package ru.anani.messenger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class MessengerApplicationTests {

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
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

}
