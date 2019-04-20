
import javax.net.ssl.SSLException;

public class Start {

	public static void main(String[] args) throws SSLException {

		Connect connect = new Connect();
		
		connect.printUsers();
			
	}
	
}
