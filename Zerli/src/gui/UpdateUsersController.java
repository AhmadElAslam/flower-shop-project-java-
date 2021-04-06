package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.User;
import enums.Actions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

/**
 * update users info for Admin
 */
public class UpdateUsersController extends GUIcontroller  {
	
	public static UpdateUsersController last;
	private ObservableList<User> ObserUsers;
	@FXML
	private TableView<User> UsersTable = new TableView<User>(); // table of products
	
	@FXML
	private TextField txtSearch;
	
		/**
		 * search in the table
		 */
		@FXML
		public void onBtnSearchClick(ActionEvent event) throws Exception {
			// search user
			
			String searchQuery = txtSearch.getText();
			
			sendRequestToServer(Actions.GetUsers,searchQuery);
			
		}
		/**
		 * delete user from database
		 */
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {

			/**
			 *  delete selected row from database
			 */
			
			// get selected item
			User user = UsersTable.getSelectionModel().getSelectedItem();
			if(user!= null)
			{
				
				// delete
    			sendRequestToServer(Actions.DeleteUser,user);
    			sendRequestToServer(Actions.GetUsers); // refresh table
				
			}
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillUsersInTable(ArrayList<User> users)
		{
			/**
			 * This function fill the table with data in ArrayList 
			 */
			
			if(ObserUsers != null) {
				// if table alredy populate
				/**Dani: didn't find better solution */
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
				TableColumn<User, String> idCol = new TableColumn<User, String>("ID");
				TableColumn<User, String> usernameCol = new TableColumn<User, String>("username");
				TableColumn<User, String> passwordCol = new TableColumn<User, String>("password");
				
				//add data to columns
				idCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("id")
		        	);

		        
				usernameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("username")
		        	);
		        
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
				        			sendRequestToServer(Actions.updateUser,user);
				                }
				            }
				        );
				
				passwordCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("password")
		        	);
				
				//update username
				passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
				passwordCol.setOnEditCommit(
				            new EventHandler<CellEditEvent<User, String>>() {
				                @Override
				                public void handle(CellEditEvent<User, String> t) {
				                	// show new name in column
				                    ((User) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setUsername(t.getNewValue());
				                    
				                    //update new name to database
				                    String newPassword = t.getNewValue();
				                    User user = (User) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    user.setPassword(newPassword);
				                    
				                    //send request to server
				        			sendRequestToServer(Actions.updateUser,user);
				                }
				            }
				        );
		        
		      //update type
				passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
		        
		        UsersTable.setItems(ObserUsers);
		        UsersTable.getColumns().addAll(idCol, usernameCol,passwordCol);
			}
		}
		
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			
			sendRequestToServer(Actions.GetUsers);
			
		}
	
}
