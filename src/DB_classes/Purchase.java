package DB_classes;

import Defines.Constants;
import Defines.PurchaseType;
import Utils.TimeAndDateUtils;

public class Purchase {

	public String mUserName;
	public String mCityName;
	public String mDate;
	public String mType;

	public boolean mIsValid;

	public Purchase(String aUserName, String aCityName, String aDate, String aType) {
		this.mUserName = aUserName;
		this.mCityName = aCityName;
		this.mDate = aDate;
		this.mType = aType;
		
		if (this.mType.equals(PurchaseType.SUBSCRIPTION)) {
			this.mIsValid  = Constants.mSubscriptionDays - TimeAndDateUtils.GetDaysLeft(this.mDate) > 0 ? true : false;
		} else {
			this.mIsValid = true;
		}
		
	}

	public String toString() {
		return ("UserName: " + this.mUserName + " ; " + "CityName: " + this.mCityName + " ; " + "Date: " + this.mDate + " ; " + "Type: " + this.mType);
	}

	public boolean equals(Purchase purchase) {
		return (
				this.mUserName.equals(purchase.mUserName) &&
				this.mCityName.equals(purchase.mCityName) &&
				this.mDate.equals(purchase.mDate) &&
				this.mType.equals(purchase.mType)
				);
	}

}
