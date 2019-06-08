package DB_classes;

public class Purchase {

	public String mUserName;
	public String mCityName;
	public String mType;

	public Purchase(String aUserName, String aCityName, String aType) {
		this.mUserName = aUserName;
		this.mCityName = aCityName;
		this.mType = aCityName;
	}

	public String toString() {
		return ("UserName: " + this.mUserName + " ; " + "CityName: " + this.mCityName + " ; " + "Type: " + this.mType);
	}

}
