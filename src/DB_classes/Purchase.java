package DB_classes;

public class Purchase {

	public String mUserName;
	public String mMapName;

	public Purchase(String aUserName, String aMapName) {
		this.mUserName = aUserName;
		this.mMapName = aMapName;
	}

	public String toString() {
		return ("UserName: " + this.mUserName + " ; " + " MapName: " + this.mMapName);
	}

}
