package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Menu for employee
 */
public class MainMenuEmployeeController extends Menu  {

	@FXML
	public void updateCatalog(MouseEvent event)  throws Exception {
		// Move to UpdateCatalog
		loadFxml("UpdateCatalogProducts.fxml");
	}
	
	@FXML
	public void surveyResult(MouseEvent event)  throws Exception {
		// Move to SatisfactionSurveyResult
		loadFxml("SurveyResult.fxml");
	}
	
	
}
