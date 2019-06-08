package DB_classes;

public class AccountUser extends AccountBase {

	public String mUserName;
	public String mCreditCard;

	public int mPurchases;

	public AccountUser(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, String aCreditCard) {
		super(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber);
		this.mUserName = aUserName;
		this.mCreditCard = aCreditCard;
	}

	public AccountUser(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, String aCreditCard, int aPurchases) {
		this(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber, aUserName, aCreditCard);
		this.mPurchases = aPurchases;
	}

}
