package Responses;

public class AccountCheckResponse {

	public boolean mIsValid;
	public Object mAccount;

	public AccountCheckResponse(boolean aIsValid, Object aAccount) {
		this.mIsValid = aIsValid;
		this.mAccount = aAccount;
	}

}
