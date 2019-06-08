package DB_classes;

import java.util.List;

public class AccountUser extends AccountBase {

	public String mUserName;
	public String mCreditCard;

	public List<Purchase> mPurchases;

	public AccountUser(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, String aCreditCard, List<Purchase> aPurchases) {
		super(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber);
		this.mUserName = aUserName;
		this.mCreditCard = aCreditCard;
		this.mPurchases = aPurchases;
	}
	public String toString() {
		return (
				"FirstName: " + this.mFirstName + " ; " +
				"LastName: " + this.mLastName + " ; " +
				"Password: " + this.mPassword + " ; " +
				"Email: " + this.mEmail + " ; " +
				"PhoneNumber: " + this.mPhoneNumber + " ; " +
				"UserName: " + this.mUserName + " ; " +
				"CreditCard: " + this.mCreditCard
				);
	}
	
}
