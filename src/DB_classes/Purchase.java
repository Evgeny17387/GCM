package DB_classes;

public class Purchase {

	public String mUserName;
	public String mCityName;
	public String mDate;

	public Purchase(String aUserName, String aCityName, String aDate) {
		this.mUserName = aUserName;
		this.mCityName = aCityName;
		this.mDate = aDate;
	}

	public String toString() {
		return ("UserName: " + this.mUserName + " ; " + "CityName: " + this.mCityName + " ; " + "Date: " + this.mDate);
	}

	public boolean equals(Purchase purchase) {
		return (
				this.mUserName.equals(purchase.mUserName) &&
				this.mCityName.equals(purchase.mCityName) &&
				this.mDate.equals(purchase.mDate)
				);
	}
	
}
