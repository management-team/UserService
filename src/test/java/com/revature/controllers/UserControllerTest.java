package com.revature.controllers;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.revature.models.User;
import com.revature.models.dto.EmailList;
import com.revature.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	UserController tester = new UserController();

	@Mock
	UserService userService;

	final List<User> idUserMap = new ArrayList<User>();
	final Map<String, User> propsUserMap = new HashMap<String, User>();

	@Test
	public void testFindAll() {
		
		final int NUM_USERS = 500;

		
		idUserMap.clear();
		for (int i = 0; i < NUM_USERS; i++) {
			idUserMap.add(new User());
		}

		int i = 0;
		for (User user : idUserMap) {
			user.setUserId(i++);
		}
		
		

		when(userService.findAll()).thenReturn(idUserMap);
		ResponseEntity<List<User>> result = tester.findAll();
		List<User> resultUser = result.getBody();
		Assert.assertEquals("check user Controller find all", idUserMap, resultUser);
		
	}

	@Test
	public void testFindById() {

		final int NUM_USERS = 500;
		final int index = (int) (NUM_USERS * Math.random());

		idUserMap.clear();
		for (int i = 0; i < NUM_USERS; i++) {
			idUserMap.add(new User());
		}

		int i = 0;
		for (User user : idUserMap) {
			user.setUserId(i++);
		}

		when(userService.findOneById(index)).thenReturn(idUserMap.get(index));

		User result = tester.findById(index);

		Assert.assertEquals("Check find by id", idUserMap.get(index), result);
	}

	@Test
	public void testFindByEmail() {
		final int NUM_USERS = 500;
		final int index = (int) (NUM_USERS * Math.random());

		propsUserMap.clear();
		final List<String> emails = new ArrayList<String>();

		emails.clear();

		// create list of emails
		for (int i = 0; i < NUM_USERS; i++) {
			emails.add(i + "@gmail.com");
		}

		// create list of users
		int i = 0;
		for (String email : emails) {
			User u = new User();

			u.setEmail(email);
			u.setUserId(i++);

			propsUserMap.put(email, u);
		}

		// get one of those emails
		final String email = emails.get(index);
		final String fakeEmail = "fake user" + index;

		// set the userService.findOneByEmail() to return the correct value
		when(userService.findOneByEmail(email)).thenReturn(propsUserMap.get(email));
		when(userService.findOneByEmail(fakeEmail)).thenReturn(propsUserMap.get(fakeEmail));

		ResponseEntity<User> result = tester.findByEmail(email);
		User respBody = result.getBody();
		HttpStatus respStatus = result.getStatusCode();

		// positive test
		Assert.assertEquals("Check find by email body", propsUserMap.get(email), respBody);
		Assert.assertEquals("Check find by email status", HttpStatus.OK, respStatus);

		ResponseEntity<User> fakeResult = tester.findByEmail(fakeEmail);
		User fakeRespBody = fakeResult.getBody();
		HttpStatus fakeRespStatus = fakeResult.getStatusCode();

		// negative test
		Assert.assertNull("Found user by email where expected none", fakeRespBody);
		Assert.assertEquals("Check find by email status", HttpStatus.NOT_FOUND, fakeRespStatus);

	}

	@Test
	public void testFindAllByCohortId() {
		User test = new User();
		List<User> whenResult = new ArrayList<User>();
		whenResult.add(test);

		when(userService.findAllByCohortId(0)).thenReturn(whenResult);

		List<User> result = tester.findAllByCohortId(0);

		Assert.assertEquals("check user Controller find all by cohort id", whenResult, result);

		System.out.println(tester);
	}

	@Test
	public void testFindUsersByEmail() {
		User test = new User();
		List<User> whenResult = new ArrayList<User>();
		whenResult.add(test);

		when(userService.findUserByPartialEmail("revature")).thenReturn(whenResult);

		ResponseEntity<List<User>> result = tester.findUserByEmail("revature");
		List<User> resultUser = result.getBody();

		Assert.assertEquals("check user Controller find users by partial email", whenResult, resultUser);

		System.out.println(tester);
	}

	@Test
	public void testFindAllByEmails() {
		final List<String> emails = new ArrayList<String>();
		emails.clear();
		emails.add("blake.kruppa@revature.com");
		EmailList theEmails = new EmailList();

		theEmails.setEmailList(emails);

		User test = new User();
		test.setEmail("blake.kruppa@revature.com");
		List<User> whenResult = new ArrayList<User>();
		whenResult.add(test);

		when(userService.findListByEmail(emails)).thenReturn(whenResult);

		List<User> result = tester.findAllByEmails(theEmails);
		User resultUser = result.get(0);
		Assert.assertEquals("check user Controller find all by emails", test, resultUser);
	}

	@Test
	public void testSave() {
//		idUserMap.clear();
//		// Saving without collision
//		// Saving invalid user
//		// Saving valid user with collision
//		fail("Not yet implemented");
	
		User testSave = new User();
		testSave.setUserId(10000);
		testSave.setFirstName("first");
		testSave.setLastName("last");
		testSave.setEmail("email");
		testSave.setPhoneNumber("0000000000");
		
		when(userService.saveUser(testSave)).thenReturn(testSave);
		
		User testResult = tester.save(testSave);
		
		Assert.assertEquals("Check user controller save", testSave, testResult);
		
	}

//	@Test
//	public void testUpdate() {
//		idUserMap.clear();
//		// Updating where user exists and valid
//		// Updating where user does not exist
//		// Updating where user is not valid
//		fail("Not yet implemented");
	
//	}

}
