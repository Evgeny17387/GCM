package ViewsItem;

import javafx.beans.property.SimpleStringProperty;

public class PurchaseView {

	   private final SimpleStringProperty city;
	   private final SimpleStringProperty date;
	 
	    public PurchaseView(String aCity, String aDate) {
	        this.city = new SimpleStringProperty(aCity);
	        this.date = new SimpleStringProperty(aDate);
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
	
}
