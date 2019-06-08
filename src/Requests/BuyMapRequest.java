package Requests;

public class BuyMapRequest {

	public String mUserName;
	public String mCityName;
	public String mType;

    public BuyMapRequest(String aUserName,String aCityName, String aType){
    	this.mUserName = aUserName;
    	this.mCityName = aCityName;
    	this.mType = aType;
    }

}
