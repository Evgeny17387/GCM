package Requests;

public class ProposeNewPriceRequest {

	public String mMapName;
    public int mProposedPrice;

    public ProposeNewPriceRequest(String aMapName, int aProposedPrice){
    	this.mMapName = aMapName;
    	this.mProposedPrice = aProposedPrice;
    }

}
