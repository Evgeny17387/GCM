package DB_classes;

public class Purchase {

	public String mUserName;
	public String mCityName;
	public String mType;

	public Purchase(String aUserName, String aCityName, String aType) {
		this.mUserName = aUserName;
		this.mCityName = aCityName;
		this.mType = aType;
	}

	public String toString() {
		return ("UserName: " + this.mUserName + " ; " + "CityName: " + this.mCityName + " ; " + "Type: " + this.mType);
	}

	public boolean equals(Purchase purchase) {
		return (
				this.mUserName.equals(purchase.mUserName) &&
				this.mCityName.equals(purchase.mCityName) &&
				this.mType.equals(purchase.mType)
				);
	}
	
}
