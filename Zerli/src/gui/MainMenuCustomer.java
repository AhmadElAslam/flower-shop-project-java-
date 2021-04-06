package gui;

import java.net.URL;
import java.util.ResourceBundle;
import enums.Actions;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Menu for customer
 */
public class MainMenuCustomer extends Menu  {
	public static MainMenuCustomer last;
	@FXML Text numberOfItems;	
	
	@FXML
	public void catalog(MouseEvent event)  throws Exception {
		loadFxml("Catalog.fxml");
	}
	
	@FXML
	public void customMade(MouseEvent event)  throws Exception {
		loadFxml("CustomOrder.fxml");
	}
	
	public void updateCountItems(int count)
	{
		this.numberOfItems.setText(count + " Items");
	}
	
	@FXML
	public void onNumberOfItems(MouseEvent event)  throws Exception {
		if(LoginController.myUser.getAuthorized()==0)
		{
			this.alertValidationWarning("Not authorized", "Shop manager must Authorize your account before you can order");
		}
		else
			loadFxml("Cart.fxml");
	}
	
	@FXML
	public void updateAccount(MouseEvent event)  throws Exception {
		
		loadFxml("UpdateMyUserData.fxml");
		
	}
	
	@FXML
	public void orderHistory(MouseEvent event)  throws Exception {
		loadFxml("OrdersHistory.fxml");
	}
	
	

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
			last = this;
			if(LoginController.myUser != null)
			{
				// add hello text
				helloText.setText("Hello, "+LoginController.myUser.getUsername());
			}
			
			// update count items
			sendRequestToServer(Actions.GetMyCartCountItems,LoginController.myUser);
			
			
		}
	
}
