package DB_classes;

import java.util.ArrayList;
import java.util.List;

public class AccountUser extends AccountBase {

	public String mUserName;
	public String mCreditCard;
	public String mSubscription;

	public List<Purchase> mPurchases;

	public AccountUser(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, String aCreditCard, List<Purchase> aPurchases, String aSubscription) {
		this(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber, aUserName, aCreditCard);
		this.mPurchases = aPurchases;
		this.mSubscription = aSubscription;
	}

	public AccountUser(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, String aCreditCard) {
		super(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber);
		this.mUserName = aUserName;
		this.mCreditCard = aCreditCard;
		this.mPurchases = new ArrayList<Purchase>();
	}

	public String toString() {
		return (
				"FirstName: " + this.mFirstName + " ; " +
				"LastName: " + this.mLastName + " ; " +
				"Password: " + this.mPassword + " ; " +
				"Email: " + this.mEmail + " ; " +
				"PhoneNumber: " + this.mPhoneNumber + " ; " +
				"UserName: " + this.mUserName + " ; " +
				"CreditCard: " + this.mCreditCard + " ; " +
				"Purchases:" +  this.mPurchases.toString() + " ; " +
				"Subscription: " + this.mSubscription
				);
	}

	public boolean equals(AccountUser accountUser) {
		return (
				super.equals(accountUser) &&
				this.mUserName.equals(accountUser.mUserName) &&
				this.mCreditCard.equals(accountUser.mCreditCard) &&
				this.mPurchases.equals(accountUser.mPurchases) &&
				this.mSubscription.equals(accountUser.mSubscription)
				);
	}
	
}
