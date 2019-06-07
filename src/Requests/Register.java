package Requests;

import DB_classes.AccountBase;

public class Register extends AccountBase {

	public String mUserName;
	public String mCreditCard;

	public Register(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, String aCreditCard) {
		super(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber);
		this.mUserName = aUserName;
		this.mCreditCard = aCreditCard;
	}

}
