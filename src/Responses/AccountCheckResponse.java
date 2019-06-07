package Responses;

public class AccountCheckResponse {

	public int mErrorCode;
	public Object mAccount;

	public AccountCheckResponse(int aErrorCode, Object aAccount) {
		this.mErrorCode = aErrorCode;
		this.mAccount = aAccount;
	}

}
