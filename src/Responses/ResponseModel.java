package Responses;

public class ResponseModel {

	public int mErrorCode;
	public Object mObject;

	public ResponseModel(int aErrorCode, Object aObject) {
		this.mErrorCode = aErrorCode;
		this.mObject = aObject;
	}

}
