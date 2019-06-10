package DB_classes;

import java.util.List;

import DB_classes.Place;

public class Map {

	public String mName;
	public int mVersion;
	public String mCity;
	public String mDescription;
	public List<Place> mPlaces;
	public int mPrice;
	public String mURL;
	
	public Map(String aName, int aVersion, String aCity, String aDescription, List<Place> aPlaces, int aPrice, String aURL){
    	this.mName = aName;
    	this.mVersion = aVersion;
    	this.mCity = aCity;
    	this.mDescription = aDescription;
    	this.mPlaces = aPlaces;
    	this.mPrice = aPrice;
    	this.mURL = aURL;
	}

	public String toString() {

		return (
				"Name:" + this.mName + " ; " +
				"Version:" + this.mVersion + " ; " +
				"City:" + this.mCity + " ; " +
				"Description:" + this.mDescription + " ; " +
				"Places:" + this.mPlaces.toString() + " ; " +
				"Price:" + this.mPrice + " ; " +
				"URL:" + this.mURL
				);

	}
	
}
