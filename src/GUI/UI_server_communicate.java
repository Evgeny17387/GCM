package GUI;


public class  UI_server_communicate {
	// Wait for reply from the server
    public static boolean mResposeFromserver = false;

	public boolean ask_server() {
	int Counter = 0;
	
	while (mResposeFromserver != true && Counter < 10) {

	    System.out.println("Awaiting server response");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Counter++;

	}

	// Check if reply has come or not

	if (Counter != 10) {
		System.out.println("Server replies");
		mResposeFromserver = false;
		return true;
	}else {
		System.out.println("Server doesn't answer");
		mResposeFromserver = false;
		return false;
	}
	

}
}

