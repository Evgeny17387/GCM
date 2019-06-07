package DB_classes;

public class AccountWorker extends AccountBase {

	public String mType;

	public AccountWorker(int aId, String aName, String aPassword, String aType) {
		super(aId, aName, aPassword);
		this.mType = aType;
	}

}
