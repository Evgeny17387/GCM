package DB_classes;

import java.util.List;

public class Purchases {
	
	public String mUserName;
	public List<Purchase> mPurchases;
	
	public Purchases(String aUserName, List<Purchase> aPurchases) {
		this.mUserName = aUserName;
		this.mPurchases = aPurchases;
	}

	public String toString() {
		return ("UserName: " + this.mUserName + " ; " + "Purchases: " + this.mPurchases.toString());
	}

	public boolean equals(Purchases aPurchases) {
		return (
				this.mUserName.equals(aPurchases.mUserName) &&
				this.mPurchases.equals(aPurchases.mPurchases)
				);
	}

}
