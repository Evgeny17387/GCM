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

	@Test
	void testUsers() {

		Model operations = new Model();

		String firstName = "firstName";
		String lastName = "lastName";
		String password = "password";
		String email = "email";
		String phoneNumber = "phoneNumber";
		String userName = "userName";
		String creditCard = "creditCard";

		// ClearTable

		Assert.assertTrue(operations.ClearTable("Users") == ErrorCodes.SUCCESS);

		// AddUser

		AccountUser accountUser = new AccountUser(firstName, lastName, password, email, phoneNumber, userName, creditCard);

		AccountCheckResponse accountCheckResponse = operations.AddUser(accountUser);

		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS);

		accountUser = (AccountUser)accountCheckResponse.mAccount;

		Assert.assertTrue(
							accountUser.mFirstName.equals(firstName) &&
							accountUser.mLastName.equals(lastName) &&
							accountUser.mPassword.equals(password) &&
							accountUser.mEmail.equals(email) &&
							accountUser.mPhoneNumber.equals(phoneNumber) &&
							accountUser.mUserName.equals(userName) &&
							accountUser.mCreditCard.equals(creditCard)
						);

		// GetUser

		accountCheckResponse = operations.GetUser(userName, password);
		
		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS);

		accountUser = (AccountUser)accountCheckResponse.mAccount;

		Assert.assertTrue(
				accountUser.mFirstName.equals(firstName) &&
				accountUser.mLastName.equals(lastName) &&
				accountUser.mPassword.equals(password) &&
				accountUser.mEmail.equals(email) &&
				accountUser.mPhoneNumber.equals(phoneNumber) &&
				accountUser.mUserName.equals(userName) &&
				accountUser.mCreditCard.equals(creditCard)
			);

		// AddUser - already exists - should Fail

		accountCheckResponse = operations.AddUser(accountUser);

		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.USER_ALREADY_EXISTS);

		// UpdateUser

		firstName = "firstName1";
		lastName = "lastName1";
		password = "password1";
		email = "email1";
		phoneNumber = "phoneNumber1";
		creditCard = "creditCard1";

		accountUser.mFirstName = firstName;
		accountUser.mLastName = lastName;
		accountUser.mPassword = password;
		accountUser.mEmail = email;
		accountUser.mPhoneNumber = phoneNumber;
		accountUser.mCreditCard = creditCard;

		accountCheckResponse = operations.UpdateUser(accountUser);

		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS);

		Assert.assertTrue(
				accountUser.mFirstName.equals(firstName) &&
				accountUser.mLastName.equals(lastName) &&
				accountUser.mPassword.equals(password) &&
				accountUser.mEmail.equals(email) &&
				accountUser.mPhoneNumber.equals(phoneNumber) &&
				accountUser.mUserName.equals(userName) &&
				accountUser.mCreditCard.equals(creditCard)
			);

		// GetUser

		accountCheckResponse = operations.GetUser(userName, password);
		
		Assert.assertTrue(accountCheckResponse.mErrorCode == ErrorCodes.SUCCESS);

		accountUser = (AccountUser)accountCheckResponse.mAccount;

		Assert.assertTrue(
				accountUser.mFirstName.equals(firstName) &&
				accountUser.mLastName.equals(lastName) &&
				accountUser.mPassword.equals(password) &&
				accountUser.mEmail.equals(email) &&
				accountUser.mPhoneNumber.equals(phoneNumber) &&
				accountUser.mUserName.equals(userName) &&
				accountUser.mCreditCard.equals(creditCard)
			);

		// Clear users table

		Assert.assertTrue(operations.ClearTable("Users") == ErrorCodes.SUCCESS);

	}

}
