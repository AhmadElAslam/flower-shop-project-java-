package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Abstract Menu class - every menu inherit from it
 * Shows user's hello message
 * Perform logout action
 */
public abstract class Menu extends GUIcontroller {
	@FXML Text helloText;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		if(LoginController.myUser != null)
		{
			// add hello text
			helloText.setText("Hello, "+LoginController.myUser.getUsername());
		}
	}
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		logout();
	}
}
