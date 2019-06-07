package MVC;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import Responses.AccountCheckResponse;

import Utils.ErrorCodes;

import MVC.Model;

import DB_classes.AccountUser;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class ModelTest {

/*

	@Test
	void testUsers() {

		Model operations = new Model();

		String name = "1";
		String password = "1";
		String email = "1";
		String creditCard = "1";

		// Clear users table
		
		Assert.assertTrue(operations.ClearTable("Users") == ErrorCodes.SUCCESS);

		// Add user

		AccountCheckResponse accountCheckResponse = operations.AddUser(name, password, email, creditCard);
		
		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS);

		AccountUser accountUser = (AccountUser)accountCheckResponse.mAccount;
		
		Assert.assertTrue(accountUser.mName.equals("1") && accountUser.mEmail.equals("1") && accountUser.mCreditCard.equals("1"));

		// Check user exists

		accountCheckResponse = operations.GetAccount("Users", name, password);
		
		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS);

		accountUser = (AccountUser)accountCheckResponse.mAccount;
		
		Assert.assertTrue(accountUser.mName.equals("1") && accountUser.mEmail.equals("1") && accountUser.mCreditCard.equals("1"));

		// Add user with the same name

		accountCheckResponse = operations.AddUser(name, password, email, creditCard);

		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.USER_ALREADY_EXISTS);

		// Clear users table

		Assert.assertTrue(operations.ClearTable("Users") == ErrorCodes.SUCCESS);

	}

*/

}
