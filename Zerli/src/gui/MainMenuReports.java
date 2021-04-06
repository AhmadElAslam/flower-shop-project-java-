package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Menu reports - shop manager, network manager
 */
public class MainMenuReports extends Menu  {
	
	
	@FXML
	public void complains(MouseEvent event)  throws Exception {
		loadFxml("ReportComplains.fxml");
	}
	
	@FXML
	public void orders(MouseEvent event)  throws Exception {
		loadFxml("ReportOrders.fxml");
	}
	
	@FXML
	public void satisfaction(MouseEvent event)  throws Exception {
		loadFxml("ReportSatisfaction.fxml");
	}
	
	@FXML
	public void revenue(MouseEvent event)  throws Exception {
		// Move to 
		loadFxml("ReportRevenues.fxml");
	}
	
	

		
	
}
