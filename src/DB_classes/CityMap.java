package DB_classes;

import java.util.List;

import DB_classes.Place;

public class CityMap {

	public String mName;
	public String mVersion;
	public String mCity;
	public String mDescription;
	public List<Place> mPlaces;
	public int mPrice;
	public String mURL;
	
	public CityMap(String aName, String version, String aCity, String aDescription, List<Place> aPlaces, int aPrice, String aURL){
    	this.mName = aName;
    	this.mVersion = version;
    	this.mCity = aCity;
    	this.mDescription = aDescription;
    	this.mPlaces = aPlaces;
    	this.mPrice = aPrice;
    	this.mURL = aURL;
	}

	public CityMap(String aName,String aDescription,String aURL){
    	this.mName = aName;
    	this.mDescription = aDescription;
    	this.mURL = aURL;
	}

	public CityMap(String aName,String aCity,String aDescription,String aURL){
    	this.mName = aName;
    	this.mDescription = aDescription;
    	this.mCity=aCity;
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

	public boolean equals(CityMap aCityMap) {
		
		return (
				
				this.mName.equals(aCityMap.mName) && 
				this.mVersion.equals(aCityMap.mVersion) &&
				this.mCity.equals(aCityMap.mCity) &&
				this.mDescription.equals(aCityMap.mDescription) &&
//				this.mPlaces.equals(aCityMap.mPlaces) &&
				this.mPrice == aCityMap.mPrice &&
				this.mURL.equals(aCityMap.mURL)
				
				);
		
	}

}
