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

	public boolean equals(AccountBase accountBase) {
		return (
				this.mFirstName.equals(accountBase.mFirstName) &&
				this.mLastName.equals(accountBase.mLastName) &&
				this.mPassword.equals(accountBase.mPassword) &&
				this.mEmail.equals(accountBase.mEmail) &&
				this.mPhoneNumber.equals(accountBase.mPhoneNumber)
				);
	}

}
