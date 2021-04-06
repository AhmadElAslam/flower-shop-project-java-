package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Address;
import entity.CreditCard;
import entity.Product;
import entity.Shop;
import entity.User;
import enums.Actions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Login
 */
public class LoginController extends GUIcontroller  {
	public static LoginController last;
	public static User myUser;
	public static CreditCard myCreditCard;
	public static Address myAddress;
	public static ArrayList<Product> cartProduct;
	public static Shop shop;
	
	@FXML private TextField loginUsername;
	@FXML private PasswordField loginPassword;
	@FXML private Button loginButton;
	@FXML private Text loginMessage;
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		loadFxml("ClientIpSetForm.fxml");
	}

	
	@FXML
	public void onLoginButtonClick(ActionEvent event) throws Exception {
		
		// clicking on logging button
		String username = loginUsername.getText();
		String password = loginPassword.getText(); 
		
		// create request to validate data
		User user = new User(username,password);
		user.setShop(shop);
		System.out.println("logincontroller shop"+shop);
		

		sendRequestToServer(Actions.ValidLoginDataCheck,user);
		
	}
	
	
	@FXML
	public void ShowLoginMessage(int isValid) throws IOException
	{
		/**
		 * show error message if data is invalid
		 */
		
		if(isValid==1) 
		{ 
			// get my address and credit card

			sendRequestToServer(Actions.GetMyAdress,myUser);
			sendRequestToServer(Actions.GetMyCreditCard,myUser);
			
			// GO to main menu
			
			
			loadFxmlMenu();
			
		}
		else if(isValid==0)
		{
			loginMessage.setText("Wrong username or password");
		}
		else if(isValid==2)
		{
			loginMessage.setText("User already logged in");
		}
		
	}
	
	
		
		public void start(Stage primaryStage) throws Exception {
			
			/**
			 * start frame
			 */
			Parent root = FXMLLoader.load(getClass().getResource("/gui/loginForm.fxml"));
			
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("/gui/ClientIpSetForm.css").toExternalForm());
			primaryStage.setTitle("Set IP");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last=this;
			
		}
		
		
	
}
