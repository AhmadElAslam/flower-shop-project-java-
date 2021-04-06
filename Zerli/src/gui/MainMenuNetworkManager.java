package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Menu for network manager
 */
public class MainMenuNetworkManager extends Menu  {
	
	@FXML Text helloText;
	@FXML ComboBox<Shop> shop1cmb;
	@FXML ComboBox<Shop> shop2cmb;

	public static Shop shop1;
	public static Shop shop2;
	int howMuchComboBox=0;
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		logout();
	}
	
	@FXML
	public void complains(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1) {
			loadFxml("ReportComplains.fxml");
		}
		if(howMuchComboBox == 2) {
			// 2 shop view
			loadFxml("ReportComplains2.fxml");
		}
	}
	
	@FXML
	public void orders(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1) {
			loadFxml("ReportOrders.fxml");
		}
		if(howMuchComboBox == 2) {
			// 2 shop view
			loadFxml("ReportOrders2.fxml");
		}
	}
	
	@FXML
	public void satisfaction(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1)
		{
			loadFxml("ReportSatisfaction.fxml");
		}
		if(howMuchComboBox == 2) {
			loadFxml("ReportSatisfaction2.fxml");
		}
	}
	
	@FXML
	public void revenue(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1)
		{
		// Move to 
			loadFxml("ReportRevenues.fxml");
		}
		if(howMuchComboBox == 2) {
			// 2 shop view
			loadFxml("ReportRevenues2.fxml");
		}
	}
	
	@FXML
	public void changeQ(ActionEvent event) throws Exception {
		/** determine which shop comboBox selected */
		boolean is1 = this.shop1cmb.getSelectionModel().isEmpty();
		boolean is2 = this.shop2cmb.getSelectionModel().isEmpty();
		howMuchComboBox = howMuchComboSelected();
		if(howMuchComboBox == 1)
		{
			if(!is1) {
				shop1 = this.shop1cmb.getSelectionModel().getSelectedItem();
			}
			if(!is2) {
				shop1 = this.shop2cmb.getSelectionModel().getSelectedItem();
			}
			
		}
		if(howMuchComboBox == 2)
		{
			shop1 = this.shop1cmb.getSelectionModel().getSelectedItem(); 
			shop2 = this.shop2cmb.getSelectionModel().getSelectedItem(); 
		}
		
		
		
	}
	

	public int howMuchComboSelected()
	{
		/** how much comboBox selected */
		boolean isq = this.shop1cmb.getSelectionModel().isEmpty();
		boolean isy = this.shop2cmb.getSelectionModel().isEmpty();
		if(isy==false && isq==false) // checks if 2 selected
			return 2;
		if(isy==true && isq==true) // checks if 2 selected
			return 0;
		if(isy==true || isq==true) // checks if 2 selected
			return 1;
		
		return 0;
	}
	

	

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			if(LoginController.myUser != null)
			{
				// add hello text
				helloText.setText("Hello, "+LoginController.myUser.getUsername());
			}
			
			// set list of shops
			this.shop1cmb.setItems(ClientIpSetController.observableList);
			this.shop2cmb.setItems(ClientIpSetController.observableList);
			
		}

}
