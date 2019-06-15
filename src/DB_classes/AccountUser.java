package DB_classes;

import java.util.ArrayList;
import java.util.List;

import Defines.PurchaseType;

public class AccountUser extends AccountBase {

	public String mUserName;
	public String mCreditCard;

	public List<Purchase> mPurchases;

	public AccountUser(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, String aCreditCard, List<Purchase> aPurchases) {
		this(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber, aUserName, aCreditCard);
		this.mPurchases = aPurchases;
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
				"Purchases:" +  this.mPurchases.toString()
				);
	}

	public boolean equals(AccountUser accountUser) {
		return (
				super.equals(accountUser) &&
				this.mUserName.equals(accountUser.mUserName) &&
				this.mCreditCard.equals(accountUser.mCreditCard) &&
				this.mPurchases.equals(accountUser.mPurchases)
				);
	}

	public boolean HasSubscription(String aCity) {

		for (Purchase purchase : mPurchases) {
			if (purchase.mCityName.equals(aCity) && purchase.mType.equals(PurchaseType.SUBSCRIPTION) && purchase.mIsValid) {
				return true;
			}
		}
		
		return false;

	}

}
