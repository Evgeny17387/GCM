package Dialogs;

import Defines.Constants;
import GUI.Main;
import javafx.concurrent.Task;

public class WaitTask extends Task<Void> {
	
    @Override
    public Void call() throws InterruptedException {

		int Counter = 0;
		
		// Awaiting loop
		
		while (Main.mResposeFromserver != true && Counter < Constants.mDelayTimeSeconds) {

            updateProgress(Counter, Constants.mDelayTimeSeconds);

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
			Main.mResposeFromserver = false;

		}else {

			System.out.println("Server doesn't answer");
			Main.mResposeFromserver = false;

		}

    	return null;

    }

};
