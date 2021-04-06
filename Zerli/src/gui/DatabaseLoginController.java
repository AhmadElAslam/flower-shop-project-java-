package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.ServerController;

/**
 * connect to server
 */
public class DatabaseLoginController implements Initializable  {
	
	@FXML
	private TextField dbName;
	@FXML
	private TextField dbUserName;
	@FXML
	private PasswordField dbPassword;
	
	@FXML
	private Button start;
	
	@FXML
		public void onServerStart(ActionEvent event) throws Exception {
			Stage stage = (Stage) start.getScene().getWindow(); 
			/**
			 * updates data to server controller. 
			 * close screen
			 */
			String name = dbName.getText();
			String user = dbUserName.getText();
			String pass = dbPassword.getText();
			
			ServerController.serverDetailsUpdate(name,user,pass);
			// conect to server
			int port;

			port = ServerController.DEFAULT_PORT; // Set port to 5555
			ServerController server = new ServerController(port);

			try {
				server.listen(); // Start listening for connections.
			} catch (Exception ex) {
				System.out.println("ERROR - Could not listen for clients!");
			}
			  
			 stage.close();
			
		}
		
		public void start(Stage primaryStage) throws Exception {
			
			/**
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/databaseLoginForm.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/main/resources/AppStyle.css").toExternalForm());
			primaryStage.setTitle("Product update tool");
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
		}
	
}
