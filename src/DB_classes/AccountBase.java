package DB_classes;

public class AccountBase {

	public int mId;
	public String mName;
	public String mPassword;

	public AccountBase(int aId, String aName, String aPassword) {
		this.mId = aId;
		this.mName = aName;
		this.mPassword = aPassword;
	}

}
