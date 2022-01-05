package com.lms.api.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lms.api.demo.DTO.UserInfoDTO;
import com.lms.api.demo.entity.Tbl_lms_user;
import com.lms.api.demo.entity.converter.LMSUserEntityConverter;
import com.lms.api.demo.service.UserDAOService;

/**
 * @author Administrator
 * This is controller class for handling User API requests
 *
 */
@RestController
//@JsonInclude(Include.NON_ABSENT)
public class LMSUserController {

	
	@Autowired
	private UserDAOService userDaoservice;
	
	@Autowired
	LMSUserEntityConverter convert = new LMSUserEntityConverter();

	@GetMapping("/Users")
	public ResponseEntity<List<UserInfoDTO>> retrieveAllUsers() {
		List<UserInfoDTO> returnList = userDaoservice.findAll();
		if(returnList.size()<=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(returnList));
	}

	
	@GetMapping("/Users/{id}")
	public ResponseEntity<UserInfoDTO> findoneuser(@PathVariable String id) throws JsonProcessingException  {
		UserInfoDTO lms_user =  userDaoservice.finOneUser(id);
		if(lms_user == null) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.of(Optional.of(lms_user));
	}
	
	@PostMapping("/Users")
	ResponseEntity<Tbl_lms_user> createUser(@RequestBody Tbl_lms_user newUser) {
		if(newUser == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		Tbl_lms_user lms_user = userDaoservice.createUser(newUser);
		return ResponseEntity.of(Optional.of(lms_user));
	}

	
	@PutMapping("/Users/{id}")
	ResponseEntity<Tbl_lms_user> updateUser(@RequestBody Tbl_lms_user newUser, @PathVariable String id) {
		
		Tbl_lms_user lms_user = userDaoservice.updateUser(newUser,id);
		if(lms_user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.of(Optional.of(lms_user));
	}

	
	@DeleteMapping("/User/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) {
		if(id!= null) {
		userDaoservice.deleteUser(id);
		return ResponseEntity
	            .status(HttpStatus.CREATED)                 
	            .body(id+" " +"Deleted");
	} else {
		return new ResponseEntity<String>(id, HttpStatus.NOT_FOUND);
	}
		
		
}
}