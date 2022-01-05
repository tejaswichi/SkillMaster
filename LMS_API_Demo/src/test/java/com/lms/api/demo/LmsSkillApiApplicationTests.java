package com.lms.api.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms.api.demo.entity.Tbl_lms_skill_master;
import com.lms.api.demo.entity.Tbl_lms_user;
import com.lms.api.demo.jpa.repository.UserSkillMasterRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class LmsSkillApiApplicationTests {
	@Autowired
	private UserSkillMasterRepository repository;

	@Test
	@Order(1)  
	void createSkill() {
		Tbl_lms_skill_master skill = new Tbl_lms_skill_master(16 , "RubyRails", LocalDateTime.now(), LocalDateTime.now());
		repository.save(skill);
		assertThat(skill).isNotNull();
		assertThat(skill.getSkill_name()).isEqualTo("RubyRails");
		//Optional<Tbl_lms_skill_master> getUserById = repository.findById(skill.getSkill_id());
		//assertThat(getUserById.get().getSkill_name()).isEqualTo(skill.getSkill_name());
	}

	@Test
	@Order(2)  
	void updateSkill() {
		Tbl_lms_skill_master skillObj = repository.findById(14).get(); 
		skillObj.setSkill_name("JavaJ2EE");
		skillObj.setLast_mod_time(LocalDateTime.now());
		repository.save(skillObj);
		Optional<Tbl_lms_skill_master> newSkill = repository.findById(14);
		assertThat(newSkill.get().getSkill_name()).isEqualTo(skillObj.getSkill_name());
	}

	@Test
	@Order(3)  
	void deleteSkill() {
		Tbl_lms_skill_master skillObj = repository.findById(20).get(); 
		repository.deleteById(skillObj.getSkill_id());
		Optional<Tbl_lms_skill_master> newSkill = repository.findById(16);
		assertThat(newSkill).isEmpty();
	}

	@Test
	void findSkillById() {
		Tbl_lms_skill_master skillObj = repository.findById(1).get();
		assertThat(skillObj).isNotNull();
		assertThat(skillObj.getSkill_id()).isEqualTo(1);
	}
	
	@Test
	void skillId_Empty() {
		String skillId="";
		assertTrue(skillId.isEmpty());
	}

	@Test
	void skillId_Null() throws Exception {
		String skillId=null;
		assertNull(skillId);
		assertThrows(NullPointerException.class, () -> {
			String skill_Id = null;
			skill_Id.length();
		});
	}

	@Test
	void skillId_IsChar() throws Exception {
		String skillId="y";
		boolean isDigit = Character.isDigit(skillId.charAt(0));
		assertFalse(skillId, isDigit);
	}


	@Test
	void skillId_IsString() throws Exception {
		String skillId="1A";
		boolean isDigit = Character.isDigit(skillId.charAt(1));
		assertFalse(skillId, isDigit);
	}

	@Test
	void skillId_ContainSpaces() {
		String skillId=" 10 ";
	    Pattern pattern = Pattern.compile(" ");
	    Matcher matcher = pattern.matcher(skillId);
	    int spaceCount = 0;
	    boolean isSpacePresent=false;
	    while (matcher.find()) {
	        spaceCount++;
	    }
	    if(spaceCount>=1)
	    	isSpacePresent=true;
	    System.out.println("spaceCount::"+spaceCount);
	    assertTrue(skillId, isSpacePresent);
	}
	
	@Test
	void skillId_ContainOnlyLetters() throws Exception {
		String skillId="AA";
		boolean isChar = false;
		if(((!skillId.equals(""))
            && (skillId != null)
            && (skillId.matches("^[a-zA-Z]*$"))))
			isChar = true;
		assertTrue(skillId, isChar);
	}

	@Test
	void skillId_ContainsSpecialCharacters() throws Exception {
		String skillId = "*12";
		boolean isSpecialChar = false;
        Pattern p = Pattern.compile(
            "[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(skillId);
        boolean res = m.find();
        if (res)
        	isSpecialChar=true;
        assertTrue(skillId, isSpecialChar);
	}

	

	@Test
	void skillID_hasOnlyDigits() throws Exception {
		String skill_id="01";
		int n = skill_id.length();
		boolean isDigit;
		for (int i = 0; i < n; i++) {
			if (Character.isDigit(skill_id.charAt(i))) {
				isDigit = true;
			}
			else {
				isDigit = false;
			}
			assertTrue(skill_id, isDigit);
		}}


	@Test
	void findAllSkills() {
		List<Tbl_lms_skill_master> skillList = repository.findAll();
		if(skillList!=null) {
			ListIterator<Tbl_lms_skill_master> skillListIterator = skillList.listIterator();
			while (skillListIterator.hasNext()) {
				System.out.println(skillListIterator.next().getSkill_id());
			}
		}
		assertThat(skillList).asList().isNotNull();
	}

}
