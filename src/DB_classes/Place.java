package DB_classes;

public class Place {

	public String mName;
	public String mClassification;

	public Place(String aName, String aClassification) {
		this.mName = aName;
		this.mClassification = aClassification;
	}

	public String toString() {

		return (
				"Name:" + this.mName + " ; " +
				"Classification:" + this.mClassification
				);

	}

}
