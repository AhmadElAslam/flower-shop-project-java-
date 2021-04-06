package client;

import gui.ClientIpSetController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientData extends Application {
	
	private static ClientIpSetController ipFrame;
		
	public static void main( String args[] ) throws Exception
	   { 
        launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		ipFrame = new ClientIpSetController(); // create StudentFrame
		ipFrame.start(arg0); 
	}

	
}
