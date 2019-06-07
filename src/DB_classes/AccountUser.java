package DB_classes;

public class AccountUser extends AccountBase {

	public int mPurchases;
	public String mEmail;
	public String mCreditCard;

	public AccountUser(int aId, String aName, String aPassword, int aPurchases, String aEmail, String aCreditCard) {
		super(aId, aName, aPassword);
		this.mPurchases = aPurchases;
		this.mEmail = aEmail;
		this.mCreditCard = aCreditCard;
	}

}
