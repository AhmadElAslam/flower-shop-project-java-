package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Complain;
import entity.User;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * complains controller - for service employee
 */
public class UpdateComplainsController extends GUIcontroller  {
	
	public static UpdateComplainsController last;
	private ObservableList<Complain> ObserComplains;
	@FXML
	private TableView<Complain> ComplainsTable = new TableView<Complain>(); // table of products
	
	@FXML private TextField compensationText;
	@FXML private TextArea descTextArea;
	@FXML private ComboBox<User> userCmb;
	
		
		public void fillComboUsers(ArrayList<User> users) {
			ObservableList obs = FXCollections.observableArrayList(users);
			userCmb.setItems(obs);
		}
		
		@FXML
		public void onAdd(ActionEvent event) throws Exception {
			// add
			User u = this.userCmb.getSelectionModel().getSelectedItem(); 
			String desc = descTextArea.getText();
			
			Complain complain = new Complain();
			complain.setUser(u);
			complain.setDesc(desc);
			
			sendRequestToServer(Actions.AddComplain,complain);
			sendRequestToServer(Actions.GetComplain);
		}
		
		/**
		 *  compensate user
		 */
		@FXML
		public void onPay(ActionEvent event) throws Exception {
			Complain complain = ComplainsTable.getSelectionModel().getSelectedItem();
			Float compensation = Float.parseFloat(compensationText.getText());
			complain.setCompensation(compensation);
			
			
			sendRequestToServer(Actions.Recompense,complain);
			sendRequestToServer(Actions.GetComplain);
			
		}
		
		/**
		 *  delete selected row from database
		 */
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {
			// get selected item
			Complain complain = ComplainsTable.getSelectionModel().getSelectedItem();
			if(complain!= null)
			{
				
				// delete
    			
    			sendRequestToServer(Actions.DeleteComplain,complain);
    			sendRequestToServer(Actions.GetComplain); // refresh table
				
			}
			
		}
		
		
		/**
		 * This function fill the table with data in ArrayList 
		 */
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<Complain> complains)
		{	
			if(ObserComplains != null) {
				// if table alredy populate
				/**Dani: didn't find better solution */
				ObserComplains.removeAll(ObserComplains);
				for(Complain c : complains)
				{
					ObserComplains.add(c);
				}
				
			}
			else
			{
				ComplainsTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserComplains = FXCollections.observableArrayList(complains);
				
				// defining table columns
				TableColumn<Complain, Integer> idCol = new TableColumn<Complain, Integer>("ID");
				TableColumn<Complain, String> descCol = new TableColumn<Complain, String>("Description");
				TableColumn<Complain, Float> compensationCol = new TableColumn<Complain, Float>("Compensation");
				TableColumn<Complain, Integer> statusCol = new TableColumn<Complain, Integer>("Status");
				
				//add data to columns
				idCol.setCellValueFactory(new PropertyValueFactory<Complain, Integer>("id"));
				descCol.setCellValueFactory(new PropertyValueFactory<Complain, String>("desc"));
				compensationCol.setCellValueFactory(new PropertyValueFactory<Complain, Float>("compensation"));
				statusCol.setCellValueFactory(new PropertyValueFactory<Complain, Integer>("status"));
				/**
		      //update username
				usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
				 usernameCol.setOnEditCommit(
				            new EventHandler<CellEditEvent<User, String>>() {
				                @Override
				                public void handle(CellEditEvent<User, String> t) {
				                	// show new name in column
				                    ((User) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setUsername(t.getNewValue());
				                    
				                    //update new name to database
				                    String newUsername = t.getNewValue();
				                    User user = (User) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    user.setUsername(newUsername);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.updateUser);
				        			req.setValue(user);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				*/
				
		        
				ComplainsTable.setItems(ObserComplains);
				ComplainsTable.getColumns().addAll(idCol, descCol,compensationCol,statusCol);
			}
		}
		
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			
			sendRequestToServer(Actions.GetComplain);
			sendRequestToServer(Actions.GetComplainUsers);
			
		}
	
}
