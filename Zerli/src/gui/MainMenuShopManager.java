package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Menu for shop manager
 */
public class MainMenuShopManager extends Menu  {

	
	@FXML
	public void authorize(MouseEvent event)  throws Exception {
		// Move to 
		loadFxml("AuthorizeUsers.fxml");
	}
	
	@FXML
	public void deals(MouseEvent event)  throws Exception {
		// move to deals
		loadFxml("UpdateDeals.fxml");
	}
	
	@FXML
	public void reports(MouseEvent event)  throws Exception {
		// move to reports
		loadFxml("MenuReport.fxml");
	}
	


	
}
