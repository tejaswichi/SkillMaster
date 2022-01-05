package com.lms.api.demo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms.api.demo.entity.Tbl_lms_userskill_map;
import com.lms.api.demo.jpa.repository.UserSkillMaprepository;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class LmsSkillMapApiDemoApplicationTests {
	@Autowired
	private UserSkillMaprepository repository;

	@Test
	@Order(1)
	public void createUserSkill() {
		Tbl_lms_userskill_map userSkill = new Tbl_lms_userskill_map("US01", 10, 12, LocalDateTime.now(), LocalDateTime.now(), "U06");
		repository.save(userSkill);
		assertThat(userSkill).isNotNull();
		assertThat(userSkill.getUser_id()).isEqualTo("U05");
	}

	@Test
	@Order(2)
	void updateUserSkill() throws Exception {
		String userSkillID = "US01";
		try {
			Tbl_lms_userskill_map skillObj = repository.findById(userSkillID).get();
			skillObj.setMonths_of_exp(30);
			skillObj.setLast_mod_time(LocalDateTime.now());
			repository.save(skillObj);
			Optional<Tbl_lms_userskill_map> newSkill = repository.findById("US01");
			assertThat(newSkill.get().getMonths_of_exp()).isEqualTo(30);
		}catch(NoSuchElementException e){
			throw new Exception("No Value Present in DB :"+userSkillID);
		}

	}

	@Test
	@Order(3)
	void deleteUserSkill() throws Exception {
		String userSkillID = "US01";
		try {
		Tbl_lms_userskill_map skillObj = repository.findById(userSkillID).get();
		boolean isExistBeforeDelete = repository.findById(userSkillID).isPresent();
		repository.deleteById(skillObj.getUser_skill_id());
		boolean isExistAfterDelete = repository.findById(userSkillID).isPresent();
		assertThat(isExistBeforeDelete).isTrue();
		assertThat(isExistAfterDelete).isFalse();
		}catch(NoSuchElementException e){
			throw new Exception("No Value Present in DB :"+userSkillID);
		}
	}


	@Test
	void findUserSkillsByID() throws Exception {
		String userSkillID = "US01";
		try {
		Tbl_lms_userskill_map skillObj = repository.findById(userSkillID).get();
		assertThat(skillObj).isNotNull();
		assertThat(skillObj.getUser_id()).isEqualTo(userSkillID);
		}catch(NoSuchElementException e){
			throw new Exception("No Value Present in DB :"+userSkillID);
		}
	}

	@Test
	void findUserSkillsByInvalidID() throws Exception {
		String userSkillID="US50";
		try {
			//Tbl_lms_userskill_map skillObj = repository.findById(skillId).get();
			assertThat(repository.findById(userSkillID).get()).isNull();
		}catch(NoSuchElementException e){
			throw new Exception("No Value Present in DB :"+userSkillID);
		}
	}

	//@Test(expected = IllegalArgumentException.class)
	@Test
	void findUserSkillsByEmptyID() {
		String userSkillID="";
		assertEquals("", userSkillID);
		//assertThat(skill_id).isNull();
		//assertFalse(skill_id.isEmpty());
		//Tbl_lms_userskill_map skillObj = repository.findById("").get();
		//assertThat(skillObj).isNull();
		//when(repository.findById("")).thenReturn(Optional.empty());
		assertThat(userSkillID).isEmpty();
		/*assertThatThrownBy(() -> repository.findById("US50"))
        //.isInstanceOf(ApiRequestException.class)
        .hasMessageContaining("Invalid id -Not found exception");*/
	} 

	@Test
	void userSkillID_Null() throws Exception {
		String userSkillID=null;
		assertNull(userSkillID);
		assertThrows(NullPointerException.class, () -> {
			String user_SkillID = null;
			user_SkillID.length();
		});
	}

	@Test
	void userSkillID_IsChar() throws Exception {
		String userSkillID="y";
		boolean isDigit = Character.isDigit(userSkillID.charAt(0));
		assertFalse(userSkillID, isDigit);
	}


	/*@Test
	void userSkillID_IsString() throws Exception {
		String userSkillID="US01";
		boolean isSecCharS = false;
		if(!Character.isDigit(userSkillID.charAt(1))&&(userSkillID.charAt(1)=='S'))
			assertTrue(userSkillID, isSecCharS);
		else
			assertFalse(userSkillID, isSecCharS);
	}*/

	@Test
	void userSkillId_StartWithUpperCase() throws Exception {
		String userSkill_id="US01";
		boolean isUpperCase =false;
		boolean isValid =false;
		if(Character.isUpperCase(userSkill_id.charAt(0))&& Character.isUpperCase(userSkill_id.charAt(1)))
		isUpperCase=true;
		if(isUpperCase)
			if(userSkill_id.charAt(0) == 'U' && userSkill_id.charAt(0) == 'S')
				assertTrue(userSkill_id, isValid);
	}

	@Test
	void userSkillId_StartWithLetterU() throws Exception {
		String user_id="U01";
		boolean isUpperCase = false;
		if(Character.isUpperCase(user_id.charAt(0)) && user_id.charAt(0) == 'U')
			isUpperCase = true;
		assertTrue(user_id, isUpperCase);
	}

	@Test
	void userSkillId_ContainSpaces() {
		String userSkillId=" US10 ";
	    Pattern pattern = Pattern.compile(" ");
	    Matcher matcher = pattern.matcher(userSkillId);
	    int spaceCount = 0;
	    boolean isSpacePresent=false;
	    while (matcher.find()) {
	        spaceCount++;
	    }
	    if(spaceCount>=1)
	    	isSpacePresent=true;
	    System.out.println("spaceCount::"+spaceCount);
	    assertTrue(userSkillId, isSpacePresent);
	}

	@Test
	void userSkillId_IsAlphaNumeric() throws Exception {
		String regEx = "[U]\\w*";
		String userSkillId="US01";
		Assertions.assertThat(userSkillId.matches(regEx));
	}

	@Test
	void userSkillId_onlyDigits() throws Exception {
		String userSkill_id="US01";
		int n = userSkill_id.length();
		boolean isDigit;
		for (int i = 2; i < n; i++) {
			if (Character.isDigit(userSkill_id.charAt(i))) {
				isDigit = true;
			}
			else {
				isDigit = false;
			}
			assertTrue(userSkill_id, isDigit);
		}}

	@Test
	void findAllUserSkills() {
		List<Tbl_lms_userskill_map> userSkillList = repository.findAll();
		if(userSkillList!=null) {
			ListIterator<Tbl_lms_userskill_map> userSkillListIterator = userSkillList.listIterator();
			while (userSkillListIterator.hasNext()) {
			}
		}
		assertThat(userSkillList).asList().isNotNull();
	}
}
