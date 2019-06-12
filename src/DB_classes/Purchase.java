package DB_classes;

public class Purchase {

	public String mUserName;
	public String mCityName;

	public Purchase(String aUserName, String aCityName) {
		this.mUserName = aUserName;
		this.mCityName = aCityName;
	}

	public String toString() {
		return ("UserName: " + this.mUserName + " ; " + "CityName: " + this.mCityName);
	}

	public boolean equals(Purchase purchase) {
		return (
				this.mUserName.equals(purchase.mUserName) &&
				this.mCityName.equals(purchase.mCityName)
				);
	}
	
}
