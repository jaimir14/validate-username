package com.exercise.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.exercise.model.Result;

public class ValidateUsernameTest {
	private static final String A_VALID_USERNAME = "aValidUsername";
	private static ValidateUsername validateClass;
	@BeforeClass
	public static void setUp(){
		validateClass = new ValidateUsername();
	}

	@Test
	public void verifyValidUsername() throws Exception {
		Result actualResult = new Result();
		actualResult = validateClass.checkUsername(A_VALID_USERNAME);
		assertTrue("Suggestion list was expected to be null", actualResult.getSugestions() == null);
		assertTrue("Username was expected to be valid", actualResult.isUsernameValid());
	}
	
	@Test
	public void verifyTwoUsersAddError() throws Exception{
		Result actualResult = new Result();
		String username = "john123";
		actualResult = validateClass.checkUsername(username);
		
		assertTrue("Suggestion list was expected to be null", actualResult.getSugestions() == null);
		assertEquals("Username was expected to be valid", actualResult.isUsernameValid(), true);

		actualResult = validateClass.checkUsername(username);
		
		assertTrue("Suggestion list was expected to have items", actualResult.getSugestions().size() > 0);
		assertFalse("Username was expected to be invalid", actualResult.isUsernameValid());		
	}
	
	@Test (expected = Exception.class)
	public void verifyExceptionThrown() throws Exception{
		String username = "no6";
		validateClass.checkUsername(username);
	}
	
	@Test
	public void verifyFalseIfContainsRestrictedWord() throws Exception{
		Result actualResult = new Result();
		String username = "cannabis123";
		actualResult = validateClass.checkUsername(username);

		assertTrue("Suggestion list was expected to have items", actualResult.getSugestions().size() > 0);
		assertFalse("Username was expected to be invalid", actualResult.isUsernameValid());			
	}
}
