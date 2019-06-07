package DB_classes;

public class AccountUser {

	public int mId;
	public String mName;
	public String mPassword;
	public int mPurchases;
	public String mEmail;
	public String mCreditCard;

	public AccountUser(int aId, String aName, String aPassword, int aPurchases, String aEmail, String aCreditCard) {
		this.mId = aId;
		this.mName = aName;
		this.mPassword = aPassword;
		this.mPurchases = aPurchases;
		this.mEmail = aEmail;
		this.mCreditCard = aCreditCard;
	}

}
