package com.lms.api.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lms.api.demo.entity.Tbl_lms_user;
import com.lms.api.demo.jpa.repository.UserRepository;

@SpringBootTest
class LmsApiDemoApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Test
	void createUserWithID() {
		Long phoneNumber = 12345678910L;
		Tbl_lms_user user = new Tbl_lms_user("", "Alan","Mike",phoneNumber, 
				"New Jersey", "EST", "https://www.linkedin.com/in/AlanMike/",
				"BS","MS", "", 
				"US-Citizen",  LocalDateTime.now(), LocalDateTime.now());
		userRepository.saveAndFlush(user);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo("Alan");
	}

	@Test
	void updateUser() {
		Tbl_lms_user userObj = userRepository.findById("U03").get();
		userObj.setName("Maria");
		userObj.setLast_mod_time(LocalDateTime.now());
		userRepository.save(userObj);
		Optional<Tbl_lms_user> newSkill = userRepository.findById("U03");
		assertThat(newSkill.get().getName()).isEqualTo("Maria");
	}

	@Test
	void deleteUser() {
		Tbl_lms_user userObj = userRepository.findById("U30").get(); 
		userRepository.deleteById(userObj.getUser_id());
		Optional<Tbl_lms_user> newSkill = userRepository.findById("U30");
		assertThat(newSkill).isEmpty();
	}

	@Test
	void findUserById() {
		Tbl_lms_user userObj = userRepository.findById("U01").get();
		assertThat(userObj).isNotNull();
		assertThat(userObj.getName()).isEqualTo("John");
	}

	@Test
	void userId_Empty() {
		String user_id="";
		assertTrue(user_id.isEmpty());
	}

	@Test
	void userId_Null() throws Exception {
		String user_id=null;
		assertNull(user_id);
		assertThrows(NullPointerException.class, () -> {
			String userId = null;
			userId.length();
		});
	}

	@Test
	void userId_IsChar() throws Exception {
		String user_id="y";
		boolean isDigit = Character.isDigit(user_id.charAt(0));
		assertFalse(user_id, isDigit);
	}


	@Test
	void userId_IsString() throws Exception {
		String user_id="UU1";
		boolean isDigit = Character.isDigit(user_id.charAt(1));
		assertFalse(user_id, isDigit);
	}

	@Test
	void userId_IsNumeric() throws Exception {
		String user_id="U01";
		boolean isDigit = Character.isDigit(user_id.charAt(1));
		assertTrue(user_id, isDigit);
	}

	@Test
	void userId_StartWithUpperCase() throws Exception {
		String user_id="U01";
		boolean isUpperCase =Character.isUpperCase(user_id.charAt(0));
		if(isUpperCase)
			if(user_id.charAt(0) == 'U')
				assertTrue(user_id, isUpperCase);
	}

	@Test
	void userId_StartWithLetterU() throws Exception {
		String user_id="U01";
		boolean isUpperCase = false;
		if(Character.isUpperCase(user_id.charAt(0)) && user_id.charAt(0) == 'U')
			isUpperCase = true;
		assertTrue(user_id, isUpperCase);
	}

	@Test
	void userId_NotStartWithLetterU() throws Exception {
		String user_id="A01";
		boolean notUFirstLetter = false;
		if(Character.isUpperCase(user_id.charAt(0))&&(user_id.charAt(0))!='U')
			notUFirstLetter = true;
		assertTrue(user_id, notUFirstLetter);
	}

	@Test
	void userId_IsAlphaNumeric() throws Exception {
		String regEx = "[U]\\w*";
		String user_id="01";
		Assertions.assertThat(user_id.matches(regEx));
	}

	@Test
	void userId_onlyDigits() throws Exception {
		String user_id="U01";
		int n = user_id.length();
		boolean isDigit;
		for (int i = 1; i < n; i++) {
			if (Character.isDigit(user_id.charAt(i))) {
				isDigit = true;
			}
			else {
				isDigit = false;
			}
			assertTrue(user_id, isDigit);
		}}

	@Test
	void findAllSkills() {
		List<Tbl_lms_user> userList = userRepository.findAll();
		if(userList!=null) {
			for (Tbl_lms_user tbl_lms_user : userList) {
				System.out.println(tbl_lms_user.getUser_id());
			}
			assertThat(userList).asList().isNotNull();
		}
	}

}
