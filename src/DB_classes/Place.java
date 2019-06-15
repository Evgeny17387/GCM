package DB_classes;

public class Place {

	public String mName;
	public String mDescription;
	public String mClassification;
	public String mURL;

	public Place(String aName,String aDescription, String aClassification, String aURL) {
		this.mName = aName;
		this.mDescription=aDescription;
		this.mClassification = aClassification;
		this.mURL = aURL;
	}
	public Place(String aName,String aDescription, String aURL) {
		this.mName = aName;
		this.mDescription=aDescription;
		this.mURL = aURL;
	}

	
	public String toString() {

		return (
				this.mName + " ; " +
				"Description:" + this.mDescription + " ; " +
				"Classification:" + this.mClassification + " ; " +
				"URL:" + this.mURL
				);

	}
	
	public boolean equals(Place aPlace) {
		
		return (
				this.mName.equals(aPlace.mName) && 
				this.mDescription.equals(aPlace.mName) && 
				this.mClassification.equals(aPlace.mName) && 
				this.mURL.equals(aPlace.mName)
				);
		
	}

}
