package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Menu for service emplyoee
 */
public class MainMenuService extends Menu  {

	
	@FXML
	public void complains(MouseEvent event)  throws Exception {
		// Move to 
		loadFxml("UpdateComplains.fxml");
	}
	
	@FXML
	public void survey(MouseEvent event)  throws Exception {
		// Move to 
		loadFxml("SatisfactionSurvey.fxml");
	}
	
	
}
