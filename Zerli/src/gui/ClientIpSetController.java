package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.Client;
import entity.Shop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * connect to server
 * select shop
 */
public class ClientIpSetController implements Initializable  {
	@FXML private TextField txtIP;
	
	@FXML private Button saveIP;
	@FXML private ComboBox<Shop> selectShop;
	public static ObservableList<Shop> observableList; // list of shops
	
		public void saveIPaction(ActionEvent event) throws Exception {
			/**
			 * update client ip
			 * start product manager
			 */
			String ip = txtIP.getText(); 
			Client.host = ip;
			LoginController.shop = this.selectShop.getSelectionModel().getSelectedItem();
			
			System.out.println("clientIp: "+this.selectShop.getSelectionModel().getSelectedItem().getShopName());
			/**
			 * HERE COME CODES THAT TAKES YOU TO THE CLASS PRODUCT MANAGER
			 * 
			  
			
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			
			Stage primaryStage = new Stage();
			
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/loginForm.fxml").openStream());
	
			Scene scene = new Scene(root);			
			//scene.getStylesheets().add(getClass().getResource("/gui/selectProductFrame.css").toExternalForm());
			
			primaryStage.setScene(scene); 		
			primaryStage.show(); 
			*/
			
			GUIcontroller guic = new GUIcontroller();
			guic.loadFxml("loginForm.fxml");
		}
		
		public void start(Stage primaryStage) throws Exception {
			
			/**
			 * start frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/ClientIpSetForm.fxml"));
					
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/main/resources/AppStyle.css").toExternalForm());
			//gui controller
			
			GUIcontroller.setCurrentScene(scene);
			
			//scene.getStylesheets().add(getClass().getResource("/gui/ClientIpSetForm.css").toExternalForm());
			primaryStage.setTitle("Set IP");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			Shop shop0 = new Shop();
			shop0.setId(0);
			shop0.setShopName("Internet");
			
			Shop shop1 = new Shop();
			shop1.setId(1);
			shop1.setShopName("Mister flower");
			
			Shop shop2 = new Shop();
			shop2.setId(2);
			shop2.setShopName("Super flower");
			
			ArrayList<Shop> shops = new ArrayList<Shop>();
			shops.add(shop0);
			shops.add(shop1);
			shops.add(shop2);
			
			observableList = FXCollections.observableList(shops);
		    this.selectShop.setItems(observableList);
		}
	
}
