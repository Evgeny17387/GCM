package DB_classes;

import java.util.List;

public class Route {

	public String mName;
	public String mCity;
	public String mDescription;
	public List<Place> mPlaces;
	
	public Route(String aName, String aCity, String aDescription, List<Place> aPlaces){
    	this.mName = aName;
    	this.mCity = aCity;
    	this.mDescription = aDescription;
    	this.mPlaces = aPlaces;
	}

	public String toString() {

		return (
				"Name:" + this.mName + " ; " +
				"City:" + this.mCity + " ; " +
				"Description:" + this.mDescription + " ; " +
				"Places:" + this.mPlaces.toString()
				);

	}

}
