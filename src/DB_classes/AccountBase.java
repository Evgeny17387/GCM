package DB_classes;

public class AccountBase {

	public String mFirstName;
	public String mLastName;
	public String mPassword;
	public String mEmail;
	public String mPhoneNumber;

	public AccountBase(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber) {
		this.mFirstName = aFirstName;
		this.mLastName = aLastName;
		this.mPassword = aPassword;
		this.mEmail = aEmail;
		this.mPhoneNumber = aPhoneNumber;
	}

}
