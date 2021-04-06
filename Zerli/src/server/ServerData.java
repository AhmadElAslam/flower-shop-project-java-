package server;
import gui.DatabaseLoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerData extends Application {
	
	private static DatabaseLoginController dbFrame;
		
	public static void main( String args[] ) throws Exception
	   { 
        launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
  	
		dbFrame = new DatabaseLoginController(); // create StudentFrame
		dbFrame.start(arg0);  
	}

	
}
