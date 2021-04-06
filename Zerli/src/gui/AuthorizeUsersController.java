package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.CreditCard;
import entity.User;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Admin page - authorize users
 */
public class AuthorizeUsersController extends GUIcontroller  {
	
	public static AuthorizeUsersController last;
	private ObservableList<User> ObserUsers;
	@FXML private TableView<User> UsersTable = new TableView<User>(); // table of products
	@FXML private TextField monthTxt;
	@FXML private TextField YearTxt;
	@FXML private TextField txtCardNumber;
	@FXML private TextField txtCVV;
	@FXML private Button btnAdd;
	@FXML ComboBox<String> subscribeCmb;
	
		
		@FXML
		public void onBtnClicked(ActionEvent event) throws Exception {
			/** authorize user*/
			String CreditCardNumber = txtCardNumber.getText();
			String cvv = txtCVV.getText();
			int month = Integer.parseInt(monthTxt.getText());
			int year = Integer.parseInt(YearTxt.getText());
			String s = this.subscribeCmb.getSelectionModel().getSelectedItem();
			User userToUpdate = UsersTable.getSelectionModel().getSelectedItem();
			int authorize=0;
			switch(s) {
			case "Regular":
				authorize=1;
				break;
			case "Monthly":
				authorize=2;
				break;
			case "Yearly":
				authorize=3;
				break;
			}
			
			CreditCard cc = new CreditCard();
			cc.setCardNumber(CreditCardNumber);
			cc.setCvv(cvv);
			cc.setExpMonth(month);
			cc.setExpYear(year);
			cc.setAuthorized(authorize);
			cc.setUser(userToUpdate);
			
			
			sendRequestToServer(Actions.AuthorizeUser,cc);
			sendRequestToServer(Actions.GetNotAuthorizedUsers);
			
		}
		
		/**
		 * fill table of users
		 */
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<User> users)
		{
			/**
			 * This function fill the users table with data in ArrayList users
			 */
			if(ObserUsers != null) {
				ObserUsers.removeAll(ObserUsers);
				for(User u : users)
				{
					ObserUsers.add(u);
				}
				
			}
			else
			{
				UsersTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserUsers = FXCollections.observableArrayList(users);
				
				// defining table columns
				TableColumn<User, String> fnameCol = new TableColumn<User, String>("First name");
				TableColumn<User, String> lnameCol = new TableColumn<User, String>("Last name");
				TableColumn<User, String> usernameCol = new TableColumn<User, String>("Username");
				TableColumn<User, String> phoneCol = new TableColumn<User, String>("phone");
				
				//add data to columns
				fnameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("fname")
		        	);
				
				lnameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("lname")
		        	);
				
				usernameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("username")
		        	);
				
				phoneCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("phone")
		        	);
		        
		        UsersTable.setItems(ObserUsers);
		        UsersTable.getColumns().addAll(fnameCol, lnameCol,usernameCol,phoneCol);
			}
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			
			sendRequestToServer(Actions.GetNotAuthorizedUsers);
			
			
			// add data to subscribe ComboBox
			ArrayList<String> autorize = new ArrayList<String>();
			autorize.add("Regular");
			autorize.add("Monthly");
			autorize.add("Yearly");
			ObservableList<String> observableListautorize = FXCollections.observableList(autorize);
			this.subscribeCmb.setItems(observableListautorize);
			this.subscribeCmb.setPromptText("Select option");
		}
}
