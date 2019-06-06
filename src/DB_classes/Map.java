package DB_classes;

import java.util.List;

import DB_classes.Place;

public class Map {

	public String mName;
	public int mVersion;
	public String mCity;
	public String mDescription;
	public List<Place> mPlaces;
	
	public Map(String aName, int aVersion, String aCity, String aDescription, List<Place> aPlaces){
    	this.mName = aName;
    	this.mVersion = aVersion;
    	this.mCity = aCity;
    	this.mDescription = aDescription;
    	this.mPlaces = aPlaces;
	}

}
