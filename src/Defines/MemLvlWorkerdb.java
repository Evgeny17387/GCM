package Defines;

import GUI.Main;

public class MemLvlWorkerdb {

	public static final String mManager 		= "Manager";
	public static final String mManagerContent 	= "ContentManager";
	public static final String mRegular 		= "Worker";

	public static void UpdateWorkerLevel() {

		switch (Main.mAccountWorker.mType) {
			case mManager:
				Main.memberlevel = MemLvl.MANAGER;
			break;
			case mManagerContent:
				Main.memberlevel = MemLvl.EDITOR_MANAGER;
			break;
			case mRegular:
				Main.memberlevel = MemLvl.WORKER;
			break;
		}

	}
	
}
