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

	public String toString() {

		return (
				this.mName   +
				"Classification:" + this.mClassification + " ; " +
				"URL:" + this.mURL
				);

	}

}
