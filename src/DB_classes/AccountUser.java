package DB_classes;

public class AccountUser extends AccountBase {

	public String mUserName;
	public int mPurchases;

	public AccountUser(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, String aUserName, int aPurchases) {
		super(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber);
		this.mUserName = aUserName;
		this.mPurchases = aPurchases;
	}

}
