package Requests;

public class BuyRequest {

	public String mUserName;
	public String mCityName;
	public String mType;

    public BuyRequest(String aUserName,String aCityName, String aType){
    	this.mUserName = aUserName;
    	this.mCityName = aCityName;
    	this.mType = aType;
    }

}
