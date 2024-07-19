package com.smartContactManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smartContactManager.service.EmailService;

@SpringBootTest
class SmartContactManagerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService email;

	@Test
	void sendEmailTest(){
    
		email.sendEmail("tripathiprashant45678@gmail.com", "Testing", "Hello");
	}

}
