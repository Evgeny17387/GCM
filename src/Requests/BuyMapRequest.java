package Requests;

public class BuyMapRequest {

	public String mUserName;
	public String mCityName;

    public BuyMapRequest(String aUserName,String aCityName, String aType){
    	this.mUserName = aUserName;
    	this.mCityName = aCityName;
    }

}
