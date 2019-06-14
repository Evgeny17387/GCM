package ViewsItem;

import javafx.beans.property.SimpleStringProperty;

public class PurchaseView {

	   private SimpleStringProperty username;
	   private SimpleStringProperty city;
	   private SimpleStringProperty date;
	   private SimpleStringProperty type;
	 
	    public PurchaseView(String aCity, String aDate, String aType) {
	        this.city = new SimpleStringProperty(aCity);
	        this.date = new SimpleStringProperty(aDate);
	        this.type = new SimpleStringProperty(aType);
	    }

	    public PurchaseView(String aCity, String aDate, String aUserName, String aType) {
	    	this(aCity, aDate, aType);
	    	this.username = new SimpleStringProperty(aUserName);
	    }

	    public String getCity() {
	        return city.get();
	    }
	    public void setFirstName(String aCity) {
	    	city.set(aCity);
	    }

	    public String getDate() {
	        return date.get();
	    }
	    public void setDate(String aDate) {
	    	date.set(aDate);
	    }

	    public String getUsername() {
	        return username.get();
	    }
	    public void setUername(String aUserName) {
	    	username.set(aUserName);
	    }

	    public String getType() {
	        return type.get();
	    }
	    public void setType(String aType) {
	    	type.set(aType);
	    }

}
