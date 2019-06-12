package ViewsItem;

import javafx.beans.property.SimpleStringProperty;

public class PurchaseView {

	   private final SimpleStringProperty city;
	 
	    public PurchaseView(String aCity) {
	        this.city = new SimpleStringProperty(aCity);
	    }
	 
	    public String getCity() {
	        return city.get();
	    }
	    public void setFirstName(String aCity) {
	    	city.set(aCity);
	    }
	
	
}
