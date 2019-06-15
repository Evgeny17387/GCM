package DB_classes;

public class CityMapUpdate {
	public String mName;
	public String mDescription;
	public String mURL;
	public String originalName;
	public CityMapUpdate(String aName,String aDescription,String aURL,String _originalName){
    	this.mName = aName;
    	this.mDescription = aDescription;
    	this.mURL = aURL;
    	this.originalName=_originalName;
	}
}
