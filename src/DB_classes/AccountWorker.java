package DB_classes;

public class AccountWorker extends AccountBase {

	public int mId;
	public String mType;

	public AccountWorker(String aFirstName, String aLastName, String aPassword, String aEmail, String aPhoneNumber, int aId, String aType) {
		super(aFirstName, aLastName, aPassword, aEmail, aPhoneNumber);
		this.mId = aId;
		this.mType = aType;
	}

	public String toString() {
		return (
				"FirstName: " + this.mFirstName + " ; " +
				"LastName: " + this.mLastName + " ; " +
				"Password: " + this.mPassword + " ; " +
				"Email: " + this.mEmail + " ; " +
				"PhoneNumber: " + this.mPhoneNumber + " ; " +
				"Type: " + this.mType + " ; " +
				"Id: " + this.mId
				);
	}

}
