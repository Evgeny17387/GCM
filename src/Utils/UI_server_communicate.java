package Utils;

import Defines.Constants;

public class  UI_server_communicate {

	// Wait for reply from the server

	public static boolean mResposeFromserver = false;

	public void ask_server() {

		int Counter = 0;
		
		while (mResposeFromserver != true && Counter < Constants.mDelayTimeSeconds) {
	
		    System.out.println("Awaiting server response");
	
	        try {
	            Thread.sleep(Constants.mTimeMiliSecondsInSecond);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	
	        Counter++;
	
		}
	
		// Check if reply has come or not
	
		if (Counter != Constants.mDelayTimeSeconds) {

			System.out.println("Server replies");
			mResposeFromserver = false;

		}else {

			System.out.println("Server doesn't answer");
			mResposeFromserver = false;

		}

	}

}
